import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main
{
	private String algoritmo;
	String cadena;
	private int numCeros;

	Main(String algoritmo, String cadena, int numCeros)
	{
		this.algoritmo = algoritmo;
		this.cadena = cadena;
		this.numCeros = numCeros;
	}

	public byte[] calcularHash256(String input) throws NoSuchAlgorithmException
	{
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		return md.digest(cadena.getBytes(StandardCharsets.UTF_8));
	}

	public byte[] calcularHash512(String input) throws NoSuchAlgorithmException
	{

		MessageDigest md = MessageDigest.getInstance("SHA-512");
		return md.digest(cadena.getBytes(StandardCharsets.UTF_8));
	}

	public void visualizarEjemplo() throws NoSuchAlgorithmException
	{
		String cadena = "cadena inicial abc";
		String v = "aalgsrj";
		System.out.println("--------EJEMPLO---------");
		System.out.println("Cadena: " + cadena);
		System.out.println("V: " + v);
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		byte[] hash = md.digest((cadena + v).getBytes(StandardCharsets.UTF_8));
		  BigInteger number = new BigInteger(1, hash); 
		  
	        // Convert message digest into hex value 
	        StringBuilder hexString = new StringBuilder(number.toString(16)); 
	  
	        // Pad with leading zeros
	        while (hexString.length() < 128) 
	        { 
	            hexString.insert(0, '0'); 
	        } 
	  
		System.out.println(hexString);
	}

	public static void main(String args[])
	{
		try
		{
			Scanner sc = new Scanner(System.in);
			System.out.println("Ingrese algoritmo a utilizar (SHA-256 ó SHA-512)");
			String algoritmo = sc.nextLine();
			System.out.println("Ingrese cadena a procesar");
			String cadena = sc.nextLine();
			System.out.println("Ingrese el número de ceros esperados");
			int numCeros = sc.nextInt();
			Main main = new Main(algoritmo, cadena, numCeros);
			
			
			
			main.visualizarEjemplo();
		}
		catch (Exception e)
		{
			System.out.println("ERROR: Favor revise el formato de la información ingresada");
			e.printStackTrace();
		}
		
	}
}
