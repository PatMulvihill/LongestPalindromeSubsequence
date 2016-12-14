import java.util.*;

public class LongestPalindromeSubsequence {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("You can type \"exit\" at any time to exit the program.");
        while (true) {
            System.out.println("Enter the word you would like to find the longest palindrome subsequence for: ");
            String inputString = input.nextLine();
            if (inputString.contentEquals("exit")) {
                break;
            }
            System.out.println(getLongestPalindromicSubsequence(inputString) + " is the longest palindrome subsequence of " + inputString + "\n");
        }
    }

    public static String getLongestPalindromicSubsequence(String inputString) {
        int inputLen = inputString.length();
        // declare a 2d array to hold the contents of longest palindrome combinations
        int[][] longestPalindrome = new int[inputLen][inputLen];
        // longestPalindrome represents length of palindrome from ith index to jth index

        // if ith index = jth index, their length is 1
        for (int i = 0; i < inputLen; i++) {
            longestPalindrome[i][i] = 1;
        }
        
        // compare the first and last indices in the input string that haven't been
        // either eliminated or included in the longestPalindrome
        for (int indexDiff = 1; indexDiff < inputLen; indexDiff++) {
            for (int i = 0; i < inputLen - indexDiff; i++) {
                int j = i + indexDiff;
                // if the first and last (unchecked) indices are the same and they are next to each other, add 2 to the longestPalindrome
                // and exit; this is the base case.
                if (inputString.charAt(i) == inputString.charAt(j) && indexDiff == 1) {
                    longestPalindrome[i][j] = 2;
                // if characters at the beginning and the end of the input string are the same, add these characters and 
                // add 2 to the longestPalindrome and move on to next unchecked indeices by calling getLongestPalindrome
                // with modified index parameters. [second index][second to last index]
                //     or more easy to understand: [most recently checked beginning index + 1][most recently checked end index - 1]
                } else if (inputString.charAt(i) == inputString.charAt(j)) {
                    longestPalindrome[i][j] = longestPalindrome[i + 1][j - 1] + 2;
                // if the characters don't match, we need to see if [first][second to last] or [second][last] match.
                // we want to take the maximum of this because we are looking for the LONGEST palindrome subsequence
                } else {
                    longestPalindrome[i][j] = Math.max(longestPalindrome[i][j - 1], longestPalindrome[i + 1][j]);
                }
            }
        }
        
        // we need to extract the relevant characters in the manipulated inputString
        StringBuffer longestPalindromeBuffer = new StringBuffer();
        int firstHalfIndex = 0, secondHalfIndex = inputLen - 1;
        while (firstHalfIndex < secondHalfIndex) {
            if (inputString.charAt(firstHalfIndex) == inputString.charAt(secondHalfIndex)) {
                longestPalindromeBuffer.append(inputString.charAt(firstHalfIndex));
                firstHalfIndex++;
                secondHalfIndex--;
            } else if (longestPalindrome[firstHalfIndex][secondHalfIndex - 1] > longestPalindrome[firstHalfIndex + 1][secondHalfIndex]) {
                secondHalfIndex--;
            } else {
                firstHalfIndex++;
            }
        }
        // string buffer to output the longest palindrome subsequence
        StringBuilder builder = new StringBuilder(longestPalindromeBuffer);
        String tempString = builder.reverse().toString();
        if (firstHalfIndex == secondHalfIndex) {
            longestPalindromeBuffer.append(inputString.charAt(firstHalfIndex)).append(tempString);
        } else {
            longestPalindromeBuffer.append(tempString);
        }
        return longestPalindromeBuffer.toString();
    }
}