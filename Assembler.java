import java.io.*;
import java.util.Scanner; // Import the Scanner class to read text files

public class Assembler {
  public static void main(String[] args) throws IOException {
    try {
      File inputFile = new File("input.txt");
      FileWriter outputFile = new FileWriter("out.hex");
      BufferedWriter bWriter = new BufferedWriter(outputFile);
      bWriter.write("v2.0 raw");
      bWriter.newLine();
      bWriter.newLine();
      Scanner scan = new Scanner(inputFile);
      while (scan.hasNextLine()) {
        String data = scan.nextLine();
        String[] input = data.split(" ");
        String[] input2 = input[1].split(",");
        String instruction = instructionToBinary(input[0], input2);
        String a = binaryToHex(instruction);
        // System.out.println(a);
        bWriter.write(a);
        bWriter.write(" ");
      }
      scan.close();
      bWriter.close();

    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  public static String binaryToHex(String binary) {
    String str = "";
    int decimal = Integer.parseInt(binary, 2);// binary to decimal.
    if ((Integer.toString(decimal, 16)).length() == 4) {// to write 0 to begining.
      str = str.concat("0");
      str = str.concat(Integer.toString(decimal, 16));

      return str;
    }
    return Integer.toString(decimal, 16);

  }

  public static String decimalToBinary(int val, int fixed) {
    char[] buff = new char[fixed];

    for (int i = fixed - 1; i >= 0; i--) {
      int mask = 1 << i;
      buff[fixed - 1 - i] = (val & mask) != 0 ? '1' : '0';
    }
    return new String(buff);
  }

  static String instructionToBinary(String first, String[] tail) {
    String binary = "";

    if (first.equalsIgnoreCase("AND")) {
      binary += "0000";
      binary += registerToBinary(tail[0]);
      binary += registerToBinary(tail[1]);
      binary += registerToBinary(tail[2]);
      binary += "00";
    }

    else if (first.equalsIgnoreCase("ANDI")) {
      binary += "0001";
      binary += registerToBinary(tail[0]);
      binary += registerToBinary(tail[1]);
      binary += decimalToBinary(Integer.parseInt(tail[2]), 6);
    } else if (first.equalsIgnoreCase("ADD")) {
      binary += "0010";
      binary += registerToBinary(tail[0]);
      binary += registerToBinary(tail[1]);
      binary += registerToBinary(tail[2]);

      binary += "00";
    } else if (first.equalsIgnoreCase("ADDI")) {
      binary += "0011";
      binary += registerToBinary(tail[0]);
      binary += registerToBinary(tail[1]);
      binary += decimalToBinary(Integer.parseInt(tail[2]), 6);
    } else if (first.equalsIgnoreCase("OR")) {
      binary += "0100";
      binary += registerToBinary(tail[0]);
      binary += registerToBinary(tail[1]);
      binary += registerToBinary(tail[2]);
      binary += "00";
    } else if (first.equalsIgnoreCase("ORI")) {
      binary += "0101";
      binary += registerToBinary(tail[0]);
      binary += registerToBinary(tail[1]);
      binary += decimalToBinary(Integer.parseInt(tail[2]), 6);
    } else if (first.equalsIgnoreCase("XOR")) {
      binary += "0110";
      binary += registerToBinary(tail[0]);
      binary += registerToBinary(tail[1]);
      binary += registerToBinary(tail[2]);
      binary += "00";
    } else if (first.equalsIgnoreCase("XORI")) {
      binary += "0111";
      binary += registerToBinary(tail[0]);
      binary += registerToBinary(tail[1]);
      binary += decimalToBinary(Integer.parseInt(tail[2]), 6);
    } else if (first.equalsIgnoreCase("JUMP")) {
      binary += "1010";
      binary += decimalToBinary(Integer.parseInt(tail[0]), 14);
    } else if (first.equalsIgnoreCase("LD")) {
      binary += "1000";
      binary += registerToBinary(tail[0]);
      binary += decimalToBinary(Integer.parseInt(tail[1]), 10);
    } else if (first.equalsIgnoreCase("ST")) {
      binary += "1001";
      binary += registerToBinary(tail[0]);
      binary += decimalToBinary(Integer.parseInt(tail[1]), 10);
    } else if (first.equalsIgnoreCase("BEQ")) {
      binary += "1011";
      binary += registerToBinary(tail[0]);
      binary += registerToBinary(tail[1]);
      binary += decimalToBinary(Integer.parseInt(tail[2]), 3);
      binary += "010";
    } else if (first.equalsIgnoreCase("BLT")) {
      binary += "1100";
      binary += registerToBinary(tail[0]);
      binary += registerToBinary(tail[1]);
      binary += decimalToBinary(Integer.parseInt(tail[2]), 3);
      binary += "100";
    } else if (first.equalsIgnoreCase("BGE")) {
      binary += "1101";
      binary += registerToBinary(tail[0]);
      binary += registerToBinary(tail[1]);
      binary += decimalToBinary(Integer.parseInt(tail[2]), 3);
      binary += "011";
    } else if (first.equalsIgnoreCase("BLE")) {
      binary += "1110";
      binary += registerToBinary(tail[0]);
      binary += registerToBinary(tail[1]);
      binary += decimalToBinary(Integer.parseInt(tail[2]), 3);
      binary += "110";
    } else if (first.equalsIgnoreCase("BGT")) {
      binary += "1111";
      binary += registerToBinary(tail[0]);
      binary += registerToBinary(tail[1]);
      binary += decimalToBinary(Integer.parseInt(tail[2]), 3);
      binary += "001";
    }
    return binary;
  }

  public static String registerToBinary(String register) {
    String[] tokens = register.split("R");

    return decimalToBinary((Integer.parseInt(tokens[1])), 4);
  }

}