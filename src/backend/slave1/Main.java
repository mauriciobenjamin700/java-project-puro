public class Main {
    public static void main(String[] args) {
        String msg = "Hello, world!";
        System.out.println(msg);
        int count = countLetters(msg);
        System.out.println("Number of letters: " + count);
    }
    public static int countLetters(String str) {
        int count = 0;
        for (char c : str.toCharArray()) {
            if (Character.isLetter(c)) {
                count++;
            }
        }
        return count;
    }
}
