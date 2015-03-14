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
    private Style style;

    private String file;
    private static String defaultFile = "output.txt";
    private static Style defaultStyle = Style.Console;

    public Printer()
    {
        this(defaultStyle,defaultFile);
    }
    public Printer(String file)
    {
        this(Style.File,file);
    }
    public Printer(Style style)
    {
        this(style,defaultFile);
    }
    private Printer(Style style, String file)
    {
        this.style = style;
        this.file = file;
    }

    public void Print(String text)
    {
        if(style == Style.Console)
            System.out.print(text);
        else if(style == Style.File)
            IO.writeFile(file,text);
    }
}
