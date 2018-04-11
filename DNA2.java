class simple
{
  String s1,s2,s3,s4,s5;
  int cols;
  int rows;
  int[][] M;
  byte[][] T;
  int gap;
  int match;
  int mismatch;

  public simple(String a, String b)
  {
    s1 = "." + a;
    s2 = "." + b;
    s3 = " ";
    s4 = " ";
    s5 = " ";

    match = 1;
    gap = 0;
    mismatch = 0;
    cols = s1.length();
    rows = s2.length();
    M = new int[rows][cols];
    T = new byte[rows][cols];
  }//end constructor

  public int score(int y, int x)
  {
    if(s1.charAt(y) == s2.charAt(x)){return match;}
    else{return mismatch;}
  }//end score

  public int D(int y, int x)
  {
    int A = M[y-1][x-1] + score(y,x);
    int B = M[y][x-1] + gap;
    int C = M[y-1][x] + gap;

    int max = Math.max(Math.max(A,B),C);

    if(A==max){T[y][x] = 1;return A;}
    else if(B==max){T[y][x] = 2;return B;}
    else{T[y][x] = 3;return C;}
  }//end matrix score

  public void initialize()
  {
    for(int y=0;y<rows;y++){M[y][0] = y*gap + gap;}
    for(int x=0;x<cols;x++){M[0][x] = x*gap + gap;}
  }//end matrix initialize

  public void fill()
  {
    for(int y=1;y<rows;y++)
      for(int x=1;x<cols;x++)
        {M[y][x] = D(y,x);}
  }//end matrix fill

  public int getScore(){return M[rows-1][cols-1];}

  public void traceback()
  {
    int y = rows-1;
    int x = cols-1;
    while(y>0 || x>0)
    {

    // System.out.println("y: " + y + "   " + "x: " + x); //+" " + "T: " + T[y][x] + " " + "M:" + M[y][x]);

      if(T[y][x] == 1)
      {
        s3 = s1.charAt(y) + s3;
        s5 = s2.charAt(x) + s5;
        if(s1.charAt(y) == s2.charAt(x)){s4 = "|" + s4;}
        else{s4 = " " + s4;}
        y--;x--;
      }

      else if(T[y][x] == 2)
      {
        s3 = "-" + s3;
        s4 = " " + s4;
        s5 = s2.charAt(x) + s5;
        x--;
      }

      else
      {
        s3 = s1.charAt(y) + s3;
        s4 = " " + s4;
        s5 = "-" + s5;
        y--;
      }
    }
  }//end traceback

  public void print()
  {
    System.out.println(s3);
    System.out.println(s4);
    System.out.println(s5);
  }
}// end class simple

class adv extends simple
{
  public adv(String a, String b)
  {super(a,b);gap=-2;match=2;mismatch=-1;}
}

public class DNA2
{
  public static void printmatrix(int[][] M, String A, String B)
   {
  //if (A.length()>30 || B.length()>20) return;
   // print first line, with chars in B:
   System.out.print("   ");
   for(int k=0;k<B.length();k++)
       System.out.printf(" %c ",B.charAt(k));
   System.out.println();
   for(int i=0;i<A.length();i++)
       {
     System.out.printf("%c ",A.charAt(i));
     for(int k=0;k<B.length();k++)
         System.out.printf("%3d",M[i][k]);
     System.out.println();
     }// for each row
   }//printmatrix

   public static String randseq(int n)
   {
     char[] S = new char[n];  // faster than building string char by char
     String DNA = "ACGT";
     for(int i=0;i<n;i++)
         {
       int r = (int)(Math.random()*4);
       S[i] = DNA.charAt(r);
         }
     return new String(S); // constructor converts char[] to String
   } // randseq

   public static void main(String[] av)
   {
    String d = "ATGCA";
    String c = "AATGC";

    simple A = new simple(c,d);

    A.initialize();
    A.fill();
    printmatrix(A.M,A.s1,A.s2);
    System.out.println("Traceback: ");
    A.traceback();
    System.out.println();
    A.print();
    System.out.println("Score: " + A.getScore());
  }
}//end class DNA2
