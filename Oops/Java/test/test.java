import java.io.*;
class Speed{
    public static void speed(int x,float y){
        double s;
        s=x/y;
        System.out.println(s);
    }
} 
class test{
     public static void main(String []args) throws IOException
     {
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    System.out.println("Enter 2 numbers");
        int d= Integer.parseInt(br.readLine());
        float t=Float.parseFloat(br.readLine());
        Speed obj= new Speed();
        obj.speed(d,t);
     }    
}