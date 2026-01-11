import com.googlecode.lanterna.*;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

public class TestLanterna {
    public static void main(String[] args) throws Exception {
        DefaultTerminalFactory dft = new DefaultTerminalFactory();
        Terminal terminal = dft.createTerminal();

        terminal.setForegroundColor(TextColor.ANSI.GREEN);

        terminal.putCharacter('h');
        terminal.putCharacter('e');
        terminal.putCharacter('l');
        terminal.putCharacter('l');
        terminal.putCharacter('o');
        terminal.putCharacter('\n');

        terminal.flush();

        terminal.setForegroundColor(TextColor.ANSI.MAGENTA);

        terminal.putCharacter('h');
        terminal.putCharacter('e');
        terminal.putCharacter('l');
        terminal.putCharacter('l');
        terminal.putCharacter('o');
        terminal.putCharacter('\n');

        terminal.flush();


        TerminalPosition startPosition = terminal.getCursorPosition();
        terminal.setCursorPosition(startPosition.withRelativeColumn(3).withRelativeRow(4));


        terminal.flush();



        Thread.sleep(2000);


        terminal.enableSGR(SGR.CROSSED_OUT);
    
        terminal.putCharacter('n');
        terminal.putCharacter('i');
        terminal.putCharacter('g');
        terminal.putCharacter('g');
        terminal.putCharacter('a');
        terminal.putCharacter('\n');

        terminal.flush();
        Thread.sleep(1000);


    }
}
