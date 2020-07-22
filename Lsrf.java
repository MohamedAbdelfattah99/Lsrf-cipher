package lsrf;

import java.util.Scanner;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Lsrf {

    public static Scanner input = new Scanner(System.in);
    public static Vector<String> buffer = new Vector<>();
    public static Vector<String> filps = new Vector<>();
    public static Vector<Integer> index = new Vector<>();
    public static Vector<String> Xor = new Vector<>();
    public static Vector<String> decpher = new Vector<>();
    public static int neg = 0;

    public static void ReadFile() throws IOException {
        File file = new File("input.txt");
        BufferedReader reader = null;
        reader = new BufferedReader(new FileReader(file));
        String text = null;
        while ((text = reader.readLine()) != null) {
            buffer.add(text);
        }
        String[] t = buffer.get(0).split("");
        String[] t2 = buffer.get(1).split("");
        for (int i = 0; i < t.length; i++) {
            filps.add(t[i]);
            Xor.add(t2[i]);
        }
        neg = Integer.parseInt(buffer.get(2));

    }

    public static String convert_to_binary(String text) {
        char c;
        String stream = "";
        int ascii;
        for (int i = 0; i < text.length(); i++) {
            String temp = "";

            c = text.charAt(i);
            ascii = (int) c;
            if(ascii<64&&ascii>=32){
                temp+="0";
            }
            
            temp += Integer.toBinaryString(ascii);

            stream += temp;
        }

        return stream;
    }

    public static void getindex() {
        for (int i = 0; i < Xor.size(); i++) {
            if (Xor.get(i).equals("1")) {
                index.add(i);
            }

        }
    }

    public static String convert_to_String(String binary) {
        String temp = "";
        String output = "";

        for (int i = 0; i < binary.length(); i += 7) {
            for (int j = i; j < i + 7; j++) {
                temp += binary.charAt(j);
            }
            int decimal = Integer.parseInt(temp, 2);
            temp = "";
            char c = (char) decimal;
            output += c;
        }

        return output;
    }

    public static void shifting() {
        String temp = "";
        for (int i = 0; i < filps.size(); i++) {
            temp += filps.get(i);
        }
        String[] arr = temp.split("");
        for (int i = 0; i < filps.size() - 1; i++) {
            filps.set(i + 1, arr[i]);
        }
    }
    public static void X_or() {
        int arr[] = new int[index.size()];
//      for (int i = 0; i < index.size(); i++) {
//           arr[i] = 0;
//        }
        for (int i = 0; i < index.size(); i++) {
            int x = index.get(i);
            arr[i] = Integer.parseInt(filps.get(x));
            // System.out.println((arr[i]));

        }
        // System.out.println(".......................");
        int res = (arr[0] ^ arr[1]);
        for (int i = 2; i < index.size(); i++) {
            res = (arr[i] ^ res);
        }
        //System.out.println(res);
        String str = String.valueOf(res);
        filps.set(0, str);
    }

    public static StringBuffer chypher(String text) throws IOException {
        String stream;
        ReadFile();
        getindex();

        stream = convert_to_binary(text);
        String buff = "";
        String output = "";
        int size = stream.length() + neg;
        for (int i = 0; i < size; i++) {
            buff += filps.get(8);
            shifting();
            X_or();
        }
        StringBuffer sb = new StringBuffer();
        for (int j = 0; j <stream.length(); j++) {
            sb.append(buff.charAt(j) ^ stream.charAt(j));
        }
        String out = "";
        for (int i = 0; i < sb.length(); i++) {
            out += sb.charAt(i);
        }
        try {
            BufferedWriter writer = null;
            writer = new BufferedWriter(new FileWriter("output.txt"));

            writer.write(out);
            writer.write("\n");
            writer.write(buff);
            writer.write("\n");
            writer.write(neg);
            writer.close();
        } catch (IOException e) {
        }
        return sb;
    }

    public static String decypher() throws FileNotFoundException, IOException {
        File file = new File("output.txt");
        BufferedReader reader = null;
        reader = new BufferedReader(new FileReader(file));
        String text = null;
        while ((text = reader.readLine()) != null) {
            decpher.add(text);
        }
        String massege = decpher.get(0);
        String key = decpher.get(1);
        String output = "";

        for (int i = 0; i < massege.length(); i++) {
            output += massege.charAt(i) ^ key.charAt(i);
        }
        String res = convert_to_String(output);
        return res;
    }

    public static void main(String[] args) throws IOException {
       /*StringBuffer sb = new StringBuffer();
        System.out.println("enter text");
        String Text = input.nextLine();
        sb = chypher(Text);
        System.out.println(sb);
        String res = decypher();
        System.out.println(sb.length());
        System.out.println("decyphr message:  " + res);
*/
//        String x=convert_to_binary("mohamed asda s4542 ahmed");
//        String y=convert_to_String(x);
//        System.out.println(y);
        System.out.println(168%64);
   
    }

}
