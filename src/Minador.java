import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Minador extends Thread
{
	public static boolean seguir;

	private String inicial;
	private int algoritmo;
	private int numCeros;
	private String asignacion;
	private boolean seguirInterno;

	public Minador(String inicial, int alg, int numCeros, String asignacion)
	{
		this.inicial = inicial;
		algoritmo = alg;
		this.numCeros = numCeros;
		this.asignacion = asignacion;
		seguirInterno = true;
	}

	public byte[] calcularHash(String input) throws NoSuchAlgorithmException
	{
		MessageDigest md;
		if (algoritmo == 512)
			md = MessageDigest.getInstance("SHA-512");
		else
			md = MessageDigest.getInstance("SHA-256");
		return md.digest(input.getBytes(StandardCharsets.UTF_8));
	}

	public int darCeros(byte[] hash, int algoritmo)
	{
		int len = 0;
		if (algoritmo == 256)
			len = 64;
		else if (algoritmo == 512)
			len = 128;
		BigInteger number = new BigInteger(1, hash);

		// Convert message digest into hex value
		StringBuilder hexString = new StringBuilder(number.toString(16));

		return (len - hexString.length()) * 4;
	}

	public void asignarMineria(String str) throws Exception
	{
		for (int i = 6; i >= 0; i--)
		{
			minar(str, 0, i);
		}
	}

	public void minar(String v, int pos, int length) throws Exception
	{
		if (seguirInterno)
		{
			if (length == 0)
			{
				String cadena = inicial + v;
				byte[] hash = calcularHash(cadena);
				int ceros = darCeros(hash, algoritmo);
				if (ceros == numCeros)
				{
					synchronized (Main.class)
					{
						seguir = false;
					}
					System.out.println(ceros);
					System.out.println("Cadena encontrada:");
					System.out.println("v = " + v);
					System.out.println("Cadena + v = " + cadena);
					seguirInterno = false;
				}
			}
			else
			{
				if (pos != 0)
				{
					pos = 0;
				}
				for (int i = 0; i < Main.busqueda.length && seguirInterno; i++)
				{
					synchronized (Main.class)
					{
						if (!seguir)
						{
							seguirInterno = false;
						}
					}
					minar(v + Main.busqueda[i], i, length - 1);
				}
			}

		}
	}

	public void run()
	{
		try
		{
			asignarMineria(asignacion);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}