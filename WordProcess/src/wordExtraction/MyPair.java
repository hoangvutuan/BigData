package wordExtraction;

public class MyPair<U extends Comparable<U>,V extends Comparable<V> > implements Comparable<MyPair<U, V>>{
	private U key;
	private V v;
	
	public U getKey() {
		return key;
	}
	public void setKey(U u) {
		this.key = u;
	}
	
	public V getV() {
		return v;
	}
	public void setV(V v) {
		this.v = v;
	}
	public MyPair(U u, V v) {
		super();
		this.key = u;
		this.v = v;
	}
	@Override
	public int compareTo(MyPair<U, V> that) {
		// TODO Auto-generated method stub
       // int cmp = this.getU().compareTo(that.getU());
		int cmp = this.getKey().toString().compareTo(that.getKey().toString());
		//int cmp = this.getU().toString()-that.getU().toString();
        return cmp;
	}
	
	public boolean equals(Object o){
	    if(o instanceof MyPair){
	        MyPair toCompare = (MyPair) o;
	        if (this.compareTo(toCompare)==0)
	        	return true;
	    }
	    return false;
	}
}
