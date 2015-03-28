/**
 * Aaron Muir
 * CS 309
 * LP Project
 */
public class Printer
{
    public enum Style
    {
        Console,
        File
    }
    public static Style style;

    public static Style getStyle()
    {
        return style;
    }

    public static void setStyle(Style style)
    {
        Printer.style = style;
    }

    public static String getFile()
    {
        return file;
    }

    public static void setFile(String file)
    {
        Printer.file = file;
    }

    public static String file;
    private static String defaultFile = "output.txt";
    private static Style defaultStyle = Style.Console;

    public static void Load()
    {
        Load(defaultStyle,defaultFile);
    }
    public static void Load(String file)
    {
        Load(Style.File, file);
    }
    public static void Load(Style style)
    {
        Load(style, defaultFile);
    }
    private static void Load(Style style, String file)
    {
        Printer.style = style;
        Printer.file = file;
    }

    public static void Print(String text)
    {
        if(style == Style.Console)
            System.out.print(text);
        else if(style == Style.File)
        {
            IO.setWriteLocation(file);
            IO.writeFile(text);
        }
    }
}
