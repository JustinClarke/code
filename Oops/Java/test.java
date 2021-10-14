public class Stringprob{
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        String word;
        word = s.next();
        word = word.replaceAll("[aeiouyAEIOUY]","");
        //the problem says that 'Y' is a vowel too
        word = insert("[^aeiouyAEIOUY]", ".");
        //here is where I lost it all
        System.out.println(word);
        }
}