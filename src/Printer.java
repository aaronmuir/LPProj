/**
 * Aaron Muir
 * Adam Julovich
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
    private static Style style;
    public static Style getStyle()
    {
        return style;
    }
    public static void setStyle(Style style)
    {
        Printer.style = style;
    }

    private static String reportFile;
    public static String getReportFile()
    {
        return reportFile;
    }
    public static void setReportFile(String reportFile)
    {
        Printer.reportFile = reportFile;
    }

    private static String logFile;
    public static String getLogFile()
    {
        return logFile;
    }
    public static void setLogFile(String logFile)
    {
        Printer.logFile = logFile;
    }

    private static String defaultFile = "output.txt";
    private static Style defaultStyle = Style.Console;

    public static void Load()
    {
        Load(defaultStyle, defaultFile);
    }
    public static void Load(String logFile)
    {
        Load(Style.File, logFile);
    }
    public static void Load(Style style)
    {
        Load(style, defaultFile);
    }
    private static void Load(Style style, String logFile)
    {
        Printer.style = style;
        Printer.logFile = logFile;
    }

    public static void Report(String text)
    {
        if(style == Style.Console)
            System.out.print(text);
        else if(style == Style.File)
            IO.writeFile(text, reportFile);
    }

    public static void Log(String text)
    {
        if(style == Style.Console)
            System.out.print(text);
        else if(style == Style.File)
            IO.writeFile(text, logFile);
    }
}
