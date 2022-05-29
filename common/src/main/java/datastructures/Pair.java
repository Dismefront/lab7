package datastructures;

/**
 * This data structure is just for simpler usage
 */

public class Pair<L, R> {

    private L p1;
    private R p2;

    public Pair(L p1, R p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public L first() {
        return p1;
    }

    public R second() {
        return p2;
    }

    public boolean equals(Object ex) {
        Pair<?, ?> q = (Pair<?, ?>) ex;
        return q.first().equals(this.first()) &&
                q.second().equals(this.second());
    }

}
