package utils;

import java.util.Scanner;

public class Input {
	Scanner scanner;

    public Input(){
        this.scanner = new Scanner(System.in);
    }

    public int getInteger(){
        return Integer.parseInt(this.scanner.nextLine());
    }
}
