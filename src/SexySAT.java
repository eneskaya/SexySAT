import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SexySAT
{

    private int[][] _formula;
    private final int _maxRows;
    private int _assignedRows;
    private int[] _positive;
    private int[] _negative;
    private final String _path;
    private final int _maxVar;

    public SexySAT(String path)
    {
        int maxVar = 0;
        int zeilen = 0;

        Scanner scanner;
        try
        {
            scanner = new Scanner(new File(path));
            scanner.next();
            scanner.next();
            maxVar = scanner.nextInt();
            zeilen = scanner.nextInt();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        _positive = new int[maxVar + 1];
        _negative = new int[maxVar + 1];
        _formula = new int[zeilen][30];
        _assignedRows = zeilen;
        _maxRows = zeilen;
        _path = path;
        _maxVar = maxVar;
    }

    public void run()
    {
        addToArray(_path);

        while (_assignedRows != 0)
        {
            int a = unitClause();

            if (a != _maxVar + 1)
            {
                unitClause(a);
            }
            else if ((a = pureLiteral()) != _maxVar + 1)
            {
                unitClause(a);
            }
            else
            {
                unitClause(add());
            }
        }

        System.out.println("SATISFIABLE! VERY SEXY!!!");
    }

    public void addToArray(String path) {
        int zeile = 0;
        int spalte = 0;
        Scanner sc;
        try
        {
            sc = new Scanner(new File(path));
            sc.nextLine();
            while (sc.hasNextInt())
            {
                int a = sc.nextInt();
                if (a == 0)
                {
                    zeile++;
                    spalte = 0;
                }
                else
                {
                    _formula[zeile][spalte] = a;
                    spalte++;
                }
            }
            sc.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    public int unitClause() {
        Set<Integer> unitClause = new HashSet<Integer>();
        int remove = _maxVar + 1;
        for (int i = 0; i < _maxRows; i++)
        {
            int x = 0;
            int last = 0;

            for (int k = 0; k < 30; k++)
            {
                if (_formula[i][k] != 0)
                {
                    x++;
                    last = k;
                }
            }

            if (x == 1)
            {
                if (unitClause.contains((_formula[i][last] * (-1))))
                {
                    System.out.println("UNSATISFIABLE! NOT SEXY!!");
                    System.exit(0);
                }
                unitClause.add(_formula[i][last]);
                remove = _formula[i][last];
            }
        }
        return remove;
    }

    public int pureLiteral() {
        int result = _maxVar + 1;
        for (int i = 0; i < _maxRows; i++)
        {
            for (int k = 0; k < 30; k++)
            {
                int num = _formula[i][k];
                if (num < 0)
                {
                    _negative[num * -1]++;
                }
                if (num > 0)
                {
                    _positive[num]++;
                }
            }
        }

        for (int i = 1; i <= _maxVar; i++)
        {
            if (_positive[i] > 0 && _negative[i] == 0)
            {
                result = i;
                return result;
            }

            if (_negative[i] > 0 && _positive[i] == 0)
            {
                result = i * (-1);
                return result;
            }
        }
        return result;
    }

    public void unitClause(int remove) {
        for (int i = 0; i < _maxRows; i++)
        {
            for (int k = 0; k < 30; k++)
            {
                if (_formula[i][k] == remove)
                {
                    for (int o = 0; o < 30; o++)
                    {
                        _formula[i][o] = 0;
                    }
                    _assignedRows = _assignedRows - 1;
                    k = 29;
                }

                if (_formula[i][k] == (remove * -1))
                {
                    _formula[i][k] = 0;
                    check(i);
                }
            }
        }
    }

    public int add() {
        int high = 0;
        int result = 0;
        for (int i = 1; i <= _maxVar; i++)
        {
            if (_positive[i] > high)
            {
                high = _positive[i];
                result = i;
            }

            if (_negative[i] > high)
            {
                high = _negative[i];
                result = i * (-1);
            }
        }
        return result;
    }

    private void check(int a) {
        for (int i = 0; i < 30; i++)
        {
            if (_formula[a][i] != 0)
            {
                return;
            }
        }
        _assignedRows = _assignedRows - 1;
    }
    
    public static void printAsciiArt() {
    	BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("src/files/foo.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	 String line = null;
    	 try {
			while ((line = br.readLine()) != null) {
			   System.out.println(line);
			 }
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public String getPath() {
    	return _path;
    }

}
