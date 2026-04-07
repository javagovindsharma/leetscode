class Robot {

    int width, height;
    int x, y;
    int dir; // 0=East, 1=North, 2=West, 3=South
    int cycle;

    public Robot(int width, int height) {
        this.width = width;
        this.height = height;
        this.x = 0;
        this.y = 0;
        this.dir = 0; // East
        this.cycle = 2 * (width + height) - 4;
    }
    
    public void step(int num) {
        if (cycle == 0) return;

        num %= cycle;

        // Special case: full cycle
        if (num == 0) num = cycle;

        while (num > 0) {
            if (dir == 0) { // East
                int move = Math.min(num, width - 1 - x);
                x += move;
                num -= move;
                if (num > 0) dir = 1;
            } 
            else if (dir == 1) { // North
                int move = Math.min(num, height - 1 - y);
                y += move;
                num -= move;
                if (num > 0) dir = 2;
            } 
            else if (dir == 2) { // West
                int move = Math.min(num, x);
                x -= move;
                num -= move;
                if (num > 0) dir = 3;
            } 
            else { // South
                int move = Math.min(num, y);
                y -= move;
                num -= move;
                if (num > 0) dir = 0;
            }
        }
    }
    
    public int[] getPos() {
        return new int[]{x, y};
    }
    
    public String getDir() {
        String[] dirs = {"East", "North", "West", "South"};
        return dirs[dir];
    }
}
