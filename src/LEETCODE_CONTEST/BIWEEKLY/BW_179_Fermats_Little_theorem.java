package LEETCODE_CONTEST.BIWEEKLY;

public class BW_179_Fermats_Little_theorem {
    private static final long MOD = 1000000007;
    long[] fact;
    long[] inv_fact;
    
    // Fast modular exponentiation — O(log pow)
    // Computes base^pow % MOD using divide and conquer:
    // base^pow = (base^(pow/2))^2        [if pow is even]
    // base^pow = (base^(pow/2))^2 * base [if pow is odd]
    // Why long params? base can be up to MOD-1 (~10^9), squaring it overflows int
    public long power(long base, long pow) {
        if (pow == 0) return 1; // base case: anything^0 = 1

        long half = power(base, pow / 2) % MOD;
        long res = (half * half) % MOD;
        if (pow % 2 != 0) res = (res * base) % MOD;

        return res;
    }

    public void precompute() {
        int len = fact.length;

        // Forward fill — fact[i] = i! % MOD
        // Must go forward since fact[i] depends on fact[i-1]
        fact[0] = 1; // 0! = 1 by definition
        for (int i = 1; i < len; i++) {
            fact[i] = (fact[i - 1] * i) % MOD;
        }

        // Seed inv_fact from the top using Fermat's Little Theorem:
        // For prime MOD: a^(MOD-2) ≡ a^(-1) (mod MOD)
        // So inv_fact[len-1] = (fact[len-1])^(MOD-2) % MOD
        // We only need ONE power() call here — the rest are derived cheaply below
        inv_fact[len - 1] = power(fact[len - 1], MOD - 2);

        // Backward fill — derived from first principles:
        // fact[i+1] = fact[i] * (i+1)
        // Taking modular inverse of both sides:
        // inv_fact[i+1] = inv_fact[i] * inv(i+1)
        // Rearranging for inv_fact[i]:
        // inv_fact[i] = inv_fact[i+1] * (i+1)
        // Why backwards? Because inv_fact[i] depends on inv_fact[i+1]
        // This avoids calling power() for every index — O(MAXN) instead of O(MAXN * log MOD)
        for (int i = len - 2; i >= 0; i--) {
            inv_fact[i] = ((i + 1) * inv_fact[i + 1]) % MOD;
        }
    }

    // O(1) nCr query after precomputation
    // Formula: n! * inv(r!) * inv((n-r)!) % MOD
    // Which is just n! / (r! * (n-r)!) in modular arithmetic
    // Why r > n-r optimization? Reduces r to smaller value — doesn't change result
    // since C(n,r) = C(n,n-r), but avoids unnecessary array access beyond bounds
    private long nCr(int n, int r) {
        if (r < 0 || r > n) return 0; // invalid selection — 0 ways

        if (r > n - r) r = n - r; // C(n,r) = C(n,n-r), use smaller r

        long nR = fact[n] % MOD;
        long dR = (inv_fact[r] % MOD * inv_fact[n - r] % MOD) % MOD;

        return (nR * dR) % MOD;
    }

    public int countVisiblePeople(int n, int pos, int k) {
        fact = new long[n + 1];
        inv_fact = new long[n + 1];

        precompute();

        // Core insight:
        // Left pool: pos people — visible to pos only if they choose 'L'
        // Right pool: n-pos-1 people — visible to pos only if they choose 'R'
        // For exactly k visible total, we split: x from left, k-x from right
        // Total ways = Σ C(pos, x) * C(n-pos-1, k-x) for x = 0 to min(k, pos)
        //
        // By Vandermonde's Identity:
        // Σ C(m, x) * C(p, k-x) = C(m+p, k)
        // Here m=pos, p=n-pos-1, so m+p = n-1
        // Therefore the sum collapses to C(n-1, k)
        //
        // pos itself can choose 'L' or 'R' freely (doesn't affect visibility count)
        // So multiply by 2 for pos's own direction assignment
        //
        // Final answer: 2 * C(n-1, k)
        long ans = (2 * nCr(n - 1, k)) % MOD;
        return (int) ans;
    }
}
