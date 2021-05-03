import java.util.Scanner;
import java.util.Date;

public class Main
{
	private String inicial;
	private int algoritmo;
	private int numCeros;
	public final static char[] busqueda =  "abcdefghijklmnopqrstuvwxyz".toCharArray();

	public void leerDatos()
	{
		Minador.seguir = true;
		try
		{
			Scanner sc = new Scanner(System.in);
			System.out.println("Ingrese algoritmo a utilizar (SHA-256 ó SHA-512)");
			String alg = sc.nextLine();
			if (alg.equals("SHA-256"))
			{
				algoritmo = 256;
			}
			else if (alg.equals("SHA-512"))
			{
				algoritmo = 512;
			}
			else
			{
				throw new Exception();
			}
			System.out.println("Ingrese cadena a procesar");
			inicial = sc.nextLine();
			System.out.println("Ingrese el número de ceros esperados");
			numCeros = sc.nextInt();
		}
		catch (Exception e)
		{
			System.out.println("Error en el formato de entrada, Favor reingrese los datos");
			System.out.println();
			leerDatos();
		}
	}
 public static void main(String args[])
	{
		try
		{
			Main main = new Main();
			while (true)
			{
				Date date = new Date();
				main.leerDatos();
				long inicio = System.nanoTime();
				Minador[] threads = new Minador[26];
				for (int i = 0; i < 26; i++)
				{
					threads[i] = new Minador(main.inicial, main.algoritmo, main.numCeros, busqueda[i]+"");
					threads[i].start();
				}
				for(Minador thread : threads)
				{
					thread.join();
				}
				long tiempo = System.nanoTime() - inicio;
				System.out.println("Tiempo de busqueda:" + tiempo/1000000000 + " segundos");
				System.out.println();
			}
			// main.visualizarEjemplo();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
