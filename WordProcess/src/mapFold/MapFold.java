package mapFold;

public class MapFold {
 
	public static int f(int x){
		if (x==4)
			return 4;
		else if (x==3)
			return 3;
		else 
			return 5;
		
		
	}
	
	public static int g(int x, int y){
		if (f(x)==4 && y==1)
			return 40;
		else if (f(x)==4 && y==30)
			return 40;
		
		if (f(x)==3 && y==1)
			return 30;
		else if (f(x)==3 && y==40)
			return 30;	
		else if (f(x)==3 && y==30)
			return 30;	
		else if (f(x)==3 && y==50)
			return 30;	
		else if (f(x)==3 && y==60)
			return 30;	
		
		if (f(x)==5 && y==40)
			return 60;
		else if (f(x)==4 && y==40)
			return 60;
		else  if (f(x)==5 && y==60)
			return 60;
		else if (f(x)==4 && y==60)
			return 60;
		else
			return 1; //nice, 60: not nice
		


			
	}

	
	
		
	public static void main(String[] args) {
		int[] a = {7, 6, 2, 4, 1};
		int[] b = new int[a.length];
		int[] c = new int[a.length];
		
		for (int i = 0; i < a.length; i++){
			b[i] = f(a[i]);
		}
		
		int x = 1;
		for (int i = 0; i < a.length; i++){
			x = g(x, b[i]);
			c[i] = x;
		}
		for (int i = 0; i < a.length; i++)
			System.out.print("\t"+ a[i] + " ");
		System.out.println();
		for (int i = 0; i < a.length; i++)
			System.out.print("\t"+ b[i] + " ");
		System.out.println();
		for (int i = 0; i < a.length; i++)
			System.out.print("\t"+ c[i] + " ");
		System.out.println();		
	}
}
