package ProductCipher;
import java.nio.file.*;

public class cipher{


	public static String readFileAsString(String filename) throws Exception
	{
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(filename)));
		return data;
	}
	public String encrypt(String substitutionInput, int key)
	{
		//Substitution encrypt
		StringBuffer substitutionOutput = new StringBuffer();
		for (int i=0; i<substitutionInput.length(); i++)
		{
			char c = substitutionInput.charAt(i);
			substitutionOutput.append((char)(c+5));
		}
		//transposition encrypt
		String transpositionInput = substitutionOutput.toString();
		int modulus;
		if ((modulus = transpositionInput.length()%key) != 0)
		{
			modulus = key-modulus;
			for (;modulus!=0; modulus--)
				transpositionInput += "/";
		}
		StringBuffer transpositionOutput = new StringBuffer();
		for (int i=0; i<key; i++)
			for (int j = 0; j<transpositionInput.length()/key;j++)
			{
				char c = transpositionInput.charAt(i+(j*key));
				transpositionOutput.append(c);
			}
		return transpositionOutput.toString();
	}
	public String decrypt(String input, int key)
	{
		StringBuffer transpositionOutput = new StringBuffer(input);
		//transposition decrypt
		key = transpositionOutput.length()/key;
		StringBuffer transpositionPlaintext = new StringBuffer();
		for(int i =0; i<key;i++)
		{
			for (int j=0; j<transpositionOutput.length()/key; j++)
			{
				char c = transpositionOutput.charAt(i+(j*key));
				transpositionPlaintext.append(c);
			}
		}
		//substitution decrypt
		StringBuffer plaintext = new StringBuffer();
		for (int i =0; i<transpositionPlaintext.length(); i++)
		{
			char c = transpositionPlaintext.charAt(i);
			plaintext.append((char)(c-5));
		}
		return plaintext.toString();
	}
}