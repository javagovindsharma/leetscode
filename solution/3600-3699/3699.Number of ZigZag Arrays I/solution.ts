function zigZagArrays(n: number, l: number, r: number): number {
    const MOD = 1_000_000_007;
    const valueCount = r - l + 1;
 
    let dpLast: number[] = new Array(valueCount).fill(1); 
    for (let length = 2; length <= n; length++) {
        const prefixSums: number[] = [0];
        for (let count of dpLast) {            
            prefixSums.push((prefixSums[prefixSums.length - 1] + count) % MOD); 
        }
 
        const dpNext: number[] = [];
        for (let index = valueCount - 1; index >= 0; index--) {            
            dpNext.push(prefixSums[index]);          
        }
        dpLast = dpNext;
    }

    const total = dpLast.reduce((sum, count) => (sum + count) % MOD, 0);
    return (total * 2) % MOD;
};
