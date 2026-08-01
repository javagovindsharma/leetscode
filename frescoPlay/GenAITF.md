# GenAI-TF-IDF — Complete Problem Statement & Tasks

## Task 1: Data Loading and Handling Missing Values

### Instructions

1. Load the training dataset (`train.csv`) and store it in a variable called `data`.

2. Display basic information about the dataset, such as:

   * Number of rows
   * Number of columns
   * Data types

3. Calculate the number of missing values in the following columns:

   * `keyword`
   * `location`

4. Drop the columns named `keyword` and `location` if too many values are missing.

### Variables

* `data`: Store the loaded dataset.
* `data_cleaned`: Store the dataset after dropping missing rows or columns.
```
import pandas as pd

### Load the data
df = pd.read_csv("train.csv")

### Clean the data
# Check missing values
print("Missing values in keyword:", df["keyword"].isnull().sum())
print("Missing values in location:", df["location"].isnull().sum())

# Drop keyword and location columns as they have many missing values
df = df.drop(columns=["keyword", "location"])
```
---

## Task 2: Data Cleaning and Text Preprocessing

### Instructions

1. In this task, focus on cleaning the `text` column.

2. Initialize stopwords and load them into a variable called `sw`.

3. Initialize a `WordNetLemmatizer` object and assign it to a variable called `lemmatizer`.

4. Create a function called `clean_text` to perform the following preprocessing steps on each tweet:

   * Remove any URLs from the text.
   * Remove any HTML tags.
   * Remove all non-alphabetical characters, including:

     * Punctuation
     * Special symbols
     * Other non-alphabetic characters
   * Remove stopwords using the NLTK stopwords list.
   * Apply lemmatization to each word using the `lemmatizer` object.
   * Remove any emojis present in the text.

5. Apply the `clean_text` function to the `text` column of the DataFrame.

6. Store the cleaned text back into the `text` column of `df`.

7. Display the first 5 rows of the cleaned data.

8. Save the first 5 rows in the variable `clean_df`.

### Variables

* `sw`: NLTK stopwords.
* `lemmatizer`: Instance of `WordNetLemmatizer`.
* `clean_text()`: Function for cleaning and preprocessing text.
* `clean_df`: Stores the first 5 rows of cleaned data.

  
```
import re
from nltk.corpus import stopwords
from nltk.stem import WordNetLemmatizer

# Initializing stopwords and lemmatizer
sw = set(stopwords.words("english"))
lemmatizer = WordNetLemmatizer()


# Define the cleaning function
def clean_text(text):
    # Convert text to string
    text = str(text)

    # Remove URLs
    text = re.sub(r"http\S+|www\S+|https\S+", "", text)

    # Remove HTML tags
    text = re.sub(r"<.*?>", "", text)

    # Remove emojis
    emoji_pattern = re.compile(
        "["
        "\U0001F600-\U0001F64F"  # emoticons
        "\U0001F300-\U0001F5FF"  # symbols & pictographs
        "\U0001F680-\U0001F6FF"  # transport & map symbols
        "\U0001F1E0-\U0001F1FF"  # flags
        "\U00002702-\U000027B0"
        "\U000024C2-\U0001F251"
        "]+",
        flags=re.UNICODE
    )
    text = re.sub(emoji_pattern,"", text)

    # Remove non-alphabetical characters
    text = re.sub(r"[^a-zA-Z\s]", "", text)

    # Convert to lowercase and split into words
    words = text.lower().split()

    # Remove stopwords and apply lemmatization
    words = [
        lemmatizer.lemmatize(word)
        for word in words
        if word not in sw
    ]

    # Join words back into text
    text = " ".join(words)

    return text


# Apply the cleaning function to the 'text' column
df["text"] = df["text"].apply(clean_text)

# Display the first 5 rows of the cleaned data
clean_df = df.head()

print(clean_df)
```
---

## Task 3: Create a Document-Term Matrix using CountVectorizer

### Instructions

1. Use the `df['text']` column from the cleaned dataset and select the first two rows as `sample_corpora` for demonstration.

2. Initialize a `CountVectorizer` object and assign it to the variable `count_vectorizer`.

3. Fit the `count_vectorizer` to the `sample_corpora` and transform the data into a document-term matrix.

4. Create a list of document names (`doc_names`) for the matrix, labeling them as:

   * `Doc0`
   * `Doc1`
   * etc.

5. Extract the feature names (tokens) from the `count_vectorizer` and store them as `feat_names`.

6. Create a DataFrame to display the document-term matrix:

   * Use document names as the index.
   * Use feature names as the columns.
   * Store the resulting DataFrame in `sample_df`.

### Variables

* `sample_corpora`: First two cleaned text rows.
* `count_vectorizer`: `CountVectorizer` object.
* `doc_names`: List of document names.
* `feat_names`: List of feature names.
* `sample_df`: DataFrame containing the document-term matrix.

```
import pandas as pd
from sklearn.feature_extraction.text import CountVectorizer

# 1. First two rows of cleaned text
sample_corpora = df["text"].head(2).tolist()

print("Sample Corpora:")
print(sample_corpora)

# 2. Initialize CountVectorizer
count_vectorizer = CountVectorizer()

# 3. Fit and transform
dtm = count_vectorizer.fit_transform(sample_corpora)

# 4. Document names
doc_names = ["Doc0", "Doc1"]

# 5. Feature names
feat_names = count_vectorizer.get_feature_names_out()

# 6. Create DataFrame
sample_df = pd.DataFrame(
    dtm.toarray(),
    index=doc_names,
    columns=feat_names
)

print(sample_df)
```
---

## Task 4: Apply TF-IDF Vectorization

### Instructions

1. **Explanation:** Use TF-IDF instead of `CountVectorizer` from Task 3 because TF-IDF better captures word importance by considering:

   * Term Frequency
   * Document Frequency

2. Split the data into training and test sets.

3. Use the `text` column as features (`X`).

4. Use the `target` column as labels (`y`).

5. Set the test size to **20%**.

6. Use `random_state=123`.

7. Ensure stratification based on the target labels.

8. Initialize a `TfidfVectorizer` object and assign it to the variable `tfidf_vectorizer`.

9. Fit the `tfidf_vectorizer` on the training data and transform it into TF-IDF vectors. Store the result in `tfidf_train_vectors`.

10. Transform the test data using the already fitted `tfidf_vectorizer`. Store the result in `tfidf_test_vectors`.

11. Display the shape of the resulting training and test vectors to ensure correct transformation.

### Variables

* `X_train`: Training features.
* `X_test`: Test features.
* `y_train`: Training labels.
* `y_test`: Test labels.
* `tfidf_vectorizer`: TF-IDF Vectorizer object.
* `tfidf_train_vectors`: TF-IDF vectors for the training set.
* `tfidf_test_vectors`: TF-IDF vectors for the test set.

```
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.model_selection import train_test_split

# Sample Data Preparation: Splitting the data into training and test sets
X = df["text"]
y = df["target"]

X_train, X_test, y_train, y_test = train_test_split(
    X,
    y,
    test_size=0.20,
    random_state=123,
    stratify=y
)

# Initialize TF-IDF Vectorizer
tfidf_vectorizer = TfidfVectorizer()

# Fit TF-IDF Vectorizer on the training data and transform the training set
tfidf_train_vectors = tfidf_vectorizer.fit_transform(X_train)

# Transform the test set using the fitted vectorizer
tfidf_test_vectors = tfidf_vectorizer.transform(X_test)

# Display shapes
print("Training vector shape:", tfidf_train_vectors.shape)
print("Test vector shape:", tfidf_test_vectors.shape)

X_train, X_test, y_train, y_test = train_test_split(
    df["text"],
    df["target"],
    test_size=0.20,
    random_state=123,
    stratify=df["target"]
```
---

## Task 5: Train and Evaluate a Random Forest Classifier

### Instructions

1. Train a `RandomForestClassifier` using:

   * `tfidf_train_vectors`
   * Corresponding training labels `y_train`

2. Predict the target labels for the test set using the trained classifier.

3. Evaluate the model's performance by generating and displaying a classification report.

4. Store the classification report in a variable named `classification_rep`.

5. Calculate the F1 score of the model and save it in the variable `flscore`.

6. Compute and visualize the confusion matrix for the predictions.

7. Create a confusion matrix and prepare it for visualization.

8. Annotate the confusion matrix with:

   * Group names
   * Counts

9. Plot the heatmap for visual representation.

### Variables

* `classifier`: Random Forest Classifier object.
* `y_pred`: Predicted labels for the test set.
* `cnf_matrix`: Confusion matrix for the test set.
* `classification_rep`: Classification report.
* `flscore`: F1 score value.

```
import numpy as np
import seaborn as sns
import matplotlib.pyplot as plt
import matplotlib.pyplot as plt

from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import (
    classification_report,
    confusion_matrix,
    f1_score
)

# Train Random Forest Classifier
classifier = RandomForestClassifier(
    random_state=123
)

classifier.fit(tfidf_train_vectors, y_train)

# Predict test labels
y_pred = classifier.predict(tfidf_test_vectors)

# Classification report
classification_rep = classification_report(
    y_test,
    y_pred
)

print("Classification Report:")
print(classification_rep)

# Calculate F1 score
f1score = f1_score(
    y_test,
    y_pred
)

print("F1 Score:", f1score)

# Create confusion matrix
cnf_matrix = confusion_matrix(
    y_test,
    y_pred
)

print("Confusion Matrix:")
print(cnf_matrix)

# Visualize confusion matrix
plt.figure(figsize=(6, 5))

sns.heatmap(
    cnf_matrix,
    annot=True,
    fmt="d",
    cmap="Blues",
    xticklabels=["Not Disaster", "Disaster"],
    yticklabels=["Not Disaster", "Disaster"]
)

plt.xlabel("Predicted Label")
plt.ylabel("Actual Label")
plt.title("Confusion Matrix")
plt.show()
```  
