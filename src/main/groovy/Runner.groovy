/**
 * Created by Shizuku Taketatsu on 7/23/15.
 */

import com.google.typography.font.sfntly.Font
import com.google.typography.font.sfntly.FontFactory
import com.google.typography.font.sfntly.Tag
import com.google.typography.font.sfntly.table.Table
import com.google.typography.font.sfntly.table.core.FontHeaderTable
import com.google.typography.font.sfntly.table.core.NameTable

class Runner
{
    static void printFontInfo(Font f)
    {
        println "======================"
        printf("%d tables in this font\n", f.numTables())

        if (f.hasTable(Tag.head))
        {
            println "Has head"
            FontHeaderTable table = f.getTable(Tag.head)
            printf("Units per EM: %d\n", table.unitsPerEm())
        }

        if (f.hasTable(Tag.hhea))
        {
            println "Has horizontal header table"
        }

        if (f.hasTable(Tag.kern))
        {
            println "Has kerning"
        }

        if (f.hasTable(Tag.name))
        {
            println "Has name table"
            NameTable nameTable = f.getTable(Tag.name)
            printf("This font has %d names\n", nameTable.nameCount())
            nameTable.iterator().each {
                println "  ====="
                if (it.nameId() == 1)
                    println "  Font Family"
                else if (it.nameId() == 4)
                    println "  Font full name"
                printf("  Name: %s\n", it.name())
                printf("%sName ID %d\n", it.nameId() == 1 || it.nameId() == 4 ? "**" : "  ", it.nameId())
                printf("  Language ID: %d\n", it.languageId())
                printf("  Encoding ID: %d\n", it.encodingId())
            }
        }
    }

    static void main(String[] args)
    {
        final String defaultFontPath = "/usr/share/fonts/truetype/DejaVuSerif.ttf";
        String fontPath = defaultFontPath

        if (args.length > 1)
        {
            File f = new File(args[0])
            if (f.exists())
                fontPath = args[0]
        }

        File file = new File(fontPath)
        Font[] fonts = null
        file.withDataInputStream {
            fonts = FontFactory.getInstance().loadFonts(it)
        }

        printf("%d font(s) in this font file %s\n", fonts.length, fontPath)

        fonts.each {printFontInfo(it)}
    }
}
