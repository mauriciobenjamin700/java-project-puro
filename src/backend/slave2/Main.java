public class Main {
    public static void main(String[] args) {
        String msg = "Hello, world 123!";
        System.out.println(msg);
        int count = countDigits(msg);
        System.out.println("Number of digits: " + count);
    }


    public static int countDigits(String str) {
        int count = 0;
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) {
                count++;
            }
        }
        return count;
    }
}
