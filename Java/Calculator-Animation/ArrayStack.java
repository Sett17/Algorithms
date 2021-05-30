Bisection.java                                                                                      0000755 0023342 0001044 00000003270 10460500144 014133  0                                                                                                    ustar   it05077                         it05aic                         0000000 0000000                                                                                                                                                                        import static java.lang.Math.*;

public class Bisection {
    // This function locates the zero of the function f in the intervall [a, b].
    // It is assumed that a < b and that the function changes the sign in the
    // interval [a, b], i.e. either 
    //        f(a) < 0 and f(b) > 0 or f(a) > 0 and f(b) < 0 
    // holds initially. The argument eps specifies the required accuracy.
    static double findZero(double a, double b, double eps) {
        assert a < b           : "a has to be less than b";
        assert f(a) * f(b) < 0 : "no sign change in interval [a, b]";
        int     count = 0;
        double  fa    = f(a); ++count;
        double  fb    = f(b); ++count;
        boolean sign  = (fa < 0);
        for (int i = 1; fa != 0.0 && fb != 0.0 && b - a > eps; ++i) {
            double c  = 0.5 * (a + b);
            double fc = f(c); ++count;
            System.out.printf(java.util.Locale.ENGLISH,
                              "%3d: a = %-12.9f, b = %-12.9f, c = %-12.9f, " +
                              "f(a) = %-10.8e, f(b) = %-10.8e, f(c) = %-10.8e, b - a = %-10e\n",
                              i, a, b, c, f(a), f(b), f(c), b - a);
            if ((sign && fc < 0.0) || (!sign && fc > 0)) {
                a = c; fa = fc; 
            } else {
                b = c; fb = fc; 
            }
        }
        System.out.println("number of function evaluations: " + count);
        return 0.5 * (a + b);
    }

    static double f(double x) {
        return x - cos(x);
        //return cos(x) - x;
        //return x * x * x * x - 1;
    }

    public static void main(String args[]) {
        System.out.println("Zero is: " + findZero(0.0, 1.0, 0.5e-9) );
    }
    
}

                                                                                                                                                                                                                                                                                                                                                    CalcGUI.java                                                                                        0000755 0023342 0001044 00000013243 10460500226 013425  0                                                                                                    ustar   it05077                         it05aic                         0000000 0000000                                                                                                                                                                        
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;


/**
   A frame with a calculator panel.
*/
class CalcGUI extends JFrame
{
	private JFrame CalcFrame;
	private CalculatorPanel panel;
	
   public CalcGUI() 
   {
	  CalcFrame = new JFrame();
	  	  
	  CalcFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      CalcFrame.setTitle("Calculator");
      panel = new CalculatorPanel();
      CalcFrame.add(panel);
      CalcFrame.pack();
      CalcFrame.setVisible(true);
   }
   
   public boolean run() {
	   if(panel.run) {
		   CalcFrame.setVisible(false);
	   }
	   return panel.run;
   }
   
   public ArrayList<Object> getTokenList() {
	   return panel.getTokenList();
   }
}

/**
   A panel with calculator buttons and a result display. 
*/
class CalculatorPanel extends JPanel
{
   public CalculatorPanel()
   {
	  run = false;
      setLayout(new BorderLayout());
      tokenList = new ArrayList<Object>();
      strPos = 0;


      result = 0;
      lastCommand = "=";
      start = true;

      // add the display

      display = new JButton("0");
      display.setEnabled(false);
      add(display, BorderLayout.NORTH);

      ActionListener insert = new InsertAction();
      ActionListener command = new CommandAction(); 

      // add the buttons in a 4 x 4 grid

      panel = new JPanel();
      panel.setLayout(new GridLayout(4, 5));

      addButton("7", insert);
      addButton("8", insert); 
      addButton("9", insert);
      addButton("/", insert);
      addButton("(", insert);
      addButton("sin", insert);

      addButton("4", insert);
      addButton("5", insert);
      addButton("6", insert);
      addButton("*", insert);
      addButton(")", insert);
      addButton("cos", insert);

      addButton("1", insert);
      addButton("2", insert);
      addButton("3", insert);
      addButton("-", insert);
      addButton("**", insert);
      addButton("sqrt", insert);

      addButton("RUN",command);
      addButton("0", insert);
      addButton(".", insert);
      addButton("+", insert);
      addButton("<-", command);
     addButton("STEP", command);
      

      add(panel, BorderLayout.CENTER);
   }

   /**
      Adds a button to the center panel.
      @param label the button label
      @param listener the button listener
   */
   private void addButton(String label, ActionListener listener) 
   {
      JButton button = new JButton(label);
      button.addActionListener(listener);
      panel.add(button);
   }

   /**
      This action inserts the button action string to the
      end of the display text. 
   */
   private class InsertAction implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {
         String input = event.getActionCommand();
         if (start)
         {
            display.setText("");
            start = false;
         }
         display.setText(display.getText() + input);
         
         //fill tokenList
         if( input == "." ){
        	 // do nothing more
         }else
         if( !Character.isDigit(input.charAt(0)) ) {
        	 
        	 //String parsing
        	 String str = new String();
        	 str = display.getText();
        	 str = str.substring(strPos,str.length()-input.length());
        	 strPos = display.getText().length();
        	 if(str.length() > 0 && 
        			 Character.isDigit(str.charAt(0))){
        	 tokenList.add(Double.parseDouble(str));
        	 tokenList.add( input );
        	 }
        	 else{
        		 tokenList.add( input );
        	 }
         }         
      }
   }

   /**
      This action executes the command that the button 
      action string denotes.
   */
   private class CommandAction implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {
         String command = event.getActionCommand ();
         
         if(command == "<-") {
        	 display.setText("0");
        	 tokenList.clear();
        	 start = true;
        	 strPos = 0;
         }
         
         if(command == "RUN" || command == "STEP") {
        	 //String parsing
        	 String str = new String();
        	 str = display.getText();
        	 str = str.substring(strPos,str.length());
        	 strPos = display.getText().length();
        	 if(Character.isDigit(display.getText().charAt(strPos - 1 ) ) ) {
        	 tokenList.add(Double.parseDouble(str));
        	 }
        	 
        	run = true;
        		 
        	 if(command == "STEP")
        		 Calculator.setStepMode(true);
        	 else
        		 Calculator.setStepMode(false);
        	 
        	 display.setText("0");
        	 start = true;
        	 strPos = 0;
        	 
         }
         
        
   	  }
   }

   /**
      Carries out the pending calculation.
      @param x the value to be accumulated with the prior result.
   */
   public void calculate(double x)
   {
      if ( lastCommand.equals("+")) result += x;
      else if (lastCommand.equals("-")) result -= x;
      else if (lastCommand.equals("*")) result *= x;
      else if (lastCommand.equals("/")) result /= x; 
      else if (lastCommand.equals("=")) result = x;
      else if (lastCommand.equals("<-")) result = x;
      display.setText("" + result);
   }
   
   public ArrayList<Object> getTokenList() {
	   return tokenList;
   }

   private JButton display;
   private JPanel panel;
   private double result;
   private String lastCommand; 
   private boolean start;
   private ArrayList<Object> tokenList;
   private int strPos;
   public boolean run;
}
                                                                                                                                                                                                                                                                                                                                                             Calculator.java                                                                                     0000755 0023342 0001044 00000023173 10460652432 014321  0                                                                                                    ustar   it05077                         it05aic                         0000000 0000000                                                                                                                                                                        import java.util.*;


public class Calculator 
{
    Stack<Double>     mArguments;
    Stack<String>     mOperators;
    Stack<Object>     mTokenStack;
    private static ArrayList<Object> tokenList;
    private boolean isRationalFunction = false;
    private static boolean stepMode = false;
    
    OutputGUI Output; 
    
    // Return true if operator op1 should be evaluated before operator op2.
    static boolean evalBefore(String op1, String op2)
    {
        if (op1.equals("(")) {
            return false;
        }
        if (precedence(op1) > precedence(op2)) {
            return true;
        } else if (precedence(op1) == precedence(op2)) {
            return op1.equals(op2) ? isLeftAssociative(op1) : true;
        } else {
            return false;
        }
    }       

    static int precedence(String operator)
    {
        if(operator.equals("+") || operator.equals("-")) {
            return 1;
        } else if ( operator.equals("*") || operator.equals("/") || 
                    operator.equals("%")) {
            return 2;
        } else if (operator.equals("**") || operator.equals("^")) {
            return 3;
        } else if(operator.equals("sqrt") || operator.equals("exp") ||
	          operator.equals("log") || operator.equals("sin") ||
	          operator.equals("cos") || operator.equals("tan"))
	{
	    return 4;	
	} else {
            System.out.println("ERROR: *** unkown operator *** ");
        }
        System.exit(1);
        return 0;
    }

    static boolean isLeftAssociative(String operator)
    {
        if (operator.equals("+") || operator.equals("-") ||
            operator.equals("*") || operator.equals("/") || 
            operator.equals("%")) {
            return true;
        } else if (operator.equals("**") || operator.equals("^")) {
            return false;
        } else {
            System.out.println("ERROR: *** unkown operator *** ");
        }
        System.exit(1);
        return false;
    }
    
    Boolean operatorType(String operator) 
    { 
    	if (operator.equals("+") || operator.equals("-") || operator.equals("*") ||
	    operator.equals("/") || operator.equals("**"))
	{
		return true;
	}
	return false;    
    }
    
    // This methods removes two arguments from the argument stack and combines
    // these arguments according to the operator on top of the operator stack.
    // This operator is then removed from the operator stack and the result of
    // the computation is pushed onto the argument stack.
    void popAndEvaluate() 
    {
    	String operator = mOperators.top();
        mOperators.pop();
        Output.moveOperantToCalculate();
	Double rhs = mArguments.top();
        mArguments.pop();
        Output.moveArgumentToCalculate(true);
	Double lhs = 0D;
	boolean LHS;
	if (LHS = operatorType(operator))
	{
        	lhs = mArguments.top();
        	mArguments.pop();
        	Output.moveArgumentToCalculate(false);
	}
        Double result = null;
        if (operator.equals("+")) {
            result = lhs + rhs;
        } else if (operator.equals("-")) {
            result = lhs - rhs;
        } else if (operator.equals("*")) {
            result = lhs * rhs;
        } else if (operator.equals("/")) {
            result = lhs / rhs;
        } else if (operator.equals("**") || operator.equals("^")) {
            result = Math.pow(lhs,rhs.intValue());       
        } else if (operator.equals("sqrt")) {
            result = Math.sqrt(rhs.intValue());       
        } else if (operator.equals("exp")) {
            result = Math.exp(rhs.intValue());       
        } else if (operator.equals("log")) {
            result = Math.log(rhs.intValue());       
        } else if (operator.equals("sin")) {
            result = Math.sin(rhs.intValue());       
        } else if (operator.equals("cos")) {
            result = Math.cos(rhs.intValue());       
        } else if (operator.equals("tan")) {
            result = Math.tan(rhs.intValue());       
        } else {
            System.out.println("ERROR: *** Unknown Operator ***");
            System.exit(1);
        }
        calcUtil cU = new calcUtil();
        result = cU.roundTo6Digits(result);
        mArguments.push(result);
       	Output.addErgebnis(result,LHS);
    }
    
    public boolean CheckTokenStackforX() {
    	String TestString;
	Stack<Object> TokenStack = null;
	try {
	  TokenStack = mTokenStack.clone();
	} catch(Exception e) {
	  e.printStackTrace();
	}
	
    	while (!TokenStack.isEmpty()) {
            if (TokenStack.top() instanceof Double) {
                TokenStack.pop();
                continue;
            } 
	    TestString = (String) TokenStack.top();
	    if ( TestString.equals("x") ) {
	   	return true;
	    }
	    else {
	      TokenStack.pop();
	    }
	}
	return false;
    }
    
    public void ReadStack(ArrayList<Object> tokenList) {
        MyScanner scanner = new MyScanner(tokenList);
        mTokenStack = scanner.getTokenStack();
        Output.setInputList(mTokenStack);
        mArguments  = new ArrayStack<Double>();
        mOperators  = new ArrayStack<String>();
                
        
	isRationalFunction = CheckTokenStackforX();
	//Debug Out
	System.out.println("Debug Out rational function:" + isRationalFunction);
    }
    
    public void SortArgsAndOps(Double x) {
    	Stack<Object> TokenStack = null;
    	try {
    		TokenStack = mTokenStack.clone();
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
	
    	while (!TokenStack.isEmpty()) {
            if (TokenStack.top() instanceof Double) {
                Double number = (Double) TokenStack.top();
                TokenStack.pop();
                mArguments.push(number);
                Output.addArgumentfromInputList();
                                
                continue;
            } 
	    
        String nextOp = (String) TokenStack.top();
	    TokenStack.pop();
	    if(isRationalFunction && nextOp.equals("x")) {
	      mArguments.push(x);
	      continue;	    	
	    }
            if (mOperators.isEmpty() || nextOp.equals("(")) {
            	mOperators.push(nextOp);
            	Output.addOperantfromInputList(nextOp);
	      //System.out.println("DebugOut: Operatoreingabe: " + mOperators.top());
              continue;
            }
            
            
            String stackOp = mOperators.top();
            if (stackOp.equals("(") && nextOp.equals(")") ) {
                Output.addOperantfromInputList(nextOp);
            	mOperators.pop();
                
            } else if (nextOp.equals(")")) {
            	popAndEvaluate();
                TokenStack.push(nextOp);
                
            } else if (evalBefore(stackOp, nextOp)) {
            	popAndEvaluate();
                TokenStack.push(nextOp);
            } else {
                mOperators.push(nextOp);
                Output.addOperantfromInputList(nextOp);
            }
        }
    }
    
    public Double evaluateTerm() {
        while (!mOperators.isEmpty()) {
	    //System.out.println("DebugOut: ausgewerterter Operator: " + mOperators.top());
            popAndEvaluate();
        }
        Double result = mArguments.top();
        mArguments.pop();
        //Output.delArg(false);
        assert mArguments.isEmpty() && mOperators.isEmpty() : "*** Syntax Error ***";
        return result;
    }
    
    public Double f(Double x) {
      //Argumente und Operatoren sortieren
      SortArgsAndOps(x);
      
      return evaluateTerm();
          
    }
    
    // This function locates the zero of the function f in the intervall [a, b].
    // It is assumed that a < b and that the function changes the sign in the
    // interval [a, b], i.e. either 
    //        f(a) < 0 and f(b) > 0 or f(a) > 0 and f(b) < 0 
    // holds initially. The argument eps specifies the required accuracy.
    public double findZero(double a, double b, double eps) {
        assert a < b           : "a has to be less than b";
        assert f(a) * f(b) < 0 : "no sign change in interval [a, b]";
        int     count = 0;
        double  fa    = f(a); ++count;
        double  fb    = f(b); ++count;
        boolean sign  = (fa < 0);
        for (int i = 1; fa != 0.0 && fb != 0.0 && b - a > eps; ++i) {
            double c  = 0.5 * (a + b);
            double fc = f(c); ++count;
            System.out.printf(java.util.Locale.ENGLISH,
                              "%3d: a = %-12.9f, b = %-12.9f, c = %-12.9f, " +
                              "f(a) = %-10.8e, f(b) = %-10.8e, f(c) = %-10.8e, b - a = %-10e\n",
                              i, a, b, c, f(a), f(b), f(c), b - a);
            if ((sign && fc < 0.0) || (!sign && fc > 0)) {
                a = c; fa = fc; 
            } else {
                b = c; fb = fc; 
            }
        }
        System.out.println("number of function evaluations: " + count);
        return 0.5 * (a + b);
    }    
    
     
    public Calculator()
    {
    	tokenList = new ArrayList<Object>();
    	CalcGUI calcframe = new CalcGUI();
    	Output = new OutputGUI();
  
    	while(!calcframe.run()) {
    	};

		tokenList = calcframe.getTokenList();
		
		//Set the OutputFrame to visible
		Output.Visible(true);
    	
    	ReadStack(tokenList);  //Reads the input stack 
    	if( isRationalFunction ) {
    		System.out.println("Zero is: " + findZero(0.0, 1.0, 5e-9) );
    	} else {
    		SortArgsAndOps(0.0);
    		System.out.println("The result is: " + evaluateTerm());
		
    	}
    }
    
    public static boolean getStepMode() {
    	return stepMode;
    }
    
    public static boolean setStepMode(boolean newMode) {
    	return stepMode = newMode;
    }
    
    public static void main(String[] args) {
    	try {
        Calculator calc = new Calculator();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}        
    }    
}

                                                                                                                                                                                                                                                                                                                                                                                                     calcUtil.java                                                                                       0000755 0023342 0001044 00000001544 10462326315 013766  0                                                                                                    ustar   it05077                         it05aic                         0000000 0000000                                                                                                                                                                        //This class calculates  diffrent needed coordinates for the stack

import java.util.*;

public class calcUtil {
	
	private static int yhigh = 20;
	private static int lettersize = 10;
		
	   public static Double roundTo6Digits(Double Zahl) {
			Zahl *= 1000000;
			Zahl = Math.ceil(Zahl);
			return Zahl /= 1000000;
	    }
	   
	   public static int relativStackKoord(int YStart, LinkedList LL) {
		   return YStart - LL.size() * yhigh;
	   }
	   
	   public static int relativStackKoord(int YStart, int AnzElement) {
		   return YStart - AnzElement * yhigh;
	   }
	   
	   public static int relativXPosition(int xPos, StringElement strE) {
		   return xPos += lettersize * (1 + strE.getStrLength());
	   }
	   
	   public static int relativXPosition(int xPos,int strLength) {
		   return xPos += lettersize * (1 + strLength);
	   }

}
                                                                                                                                                            GraphicElement.java                                                                                 0000755 0023342 0001044 00000001307 10460264254 015113  0                                                                                                    ustar   it05077                         it05aic                         0000000 0000000                                                                                                                                                                        import java.awt.*;

abstract class GraphicElement {
	 private Point Position;
	
	GraphicElement() {
		Position = new Point();
		Position.x=0;
		Position.y=0;
	}
	
	GraphicElement(Point p) {
		Position = new Point();
		try {
			Position = (Point) p.clone();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	GraphicElement(int x, int y) {
		Position = new Point();
		Position.x = x;
		Position.y = y;
	}	
	
	public int getPosX() {
		return Position.x;
	}
	
	public int getPosY() {
		return Position.y;
	}
	
	public Point getPos() {
		return Position;
	}
	
	public void setPos(Point p) {
		Position = p;
	}
	
	abstract public void paintElement(Graphics g);
}
                                                                                                                                                                                                                                                                                                                         ListStack.java                                                                                      0000755 0023342 0001044 00000002734 10460220174 014123  0                                                                                                    ustar   it05077                         it05aic                         0000000 0000000                                                                                                                                                                        public class ListStack<Element> extends Stack<Element>
{
    class DataPointerPair
    {
        Element         mData;
        DataPointerPair mNextPointer;
        
        DataPointerPair(Element data, DataPointerPair nextPointer) {
            mData        = data;
            mNextPointer = nextPointer;
        }
        
        DataPointerPair recursiveCopy(DataPointerPair pointer) {
            if (pointer == null) {
                return pointer;
            } else {
                Element data = pointer.mData;
                DataPointerPair nextPointer = recursiveCopy(pointer.mNextPointer);
                return new DataPointerPair(data, nextPointer);
            }
        }
    }

    DataPointerPair mPointer;
    
    public ListStack() {
        mPointer = null;
    }
    
    public void push(Element e) {
        mPointer = new DataPointerPair(e, mPointer);
    }
    
    public void pop() {
        assert mPointer != null : "Stack underflow!";
        mPointer = mPointer.mNextPointer;
    }

    public Element top() {
        assert mPointer != null : "Stack is empty!";
        return mPointer.mData;
    }
    
    public boolean isEmpty() {
        return mPointer == null;
    }   

    public ListStack<Element> clone() throws CloneNotSupportedException {
        ListStack<Element> result = new ListStack<Element>();
        if (mPointer != null) {
            result.mPointer = mPointer.recursiveCopy(mPointer);
        }
        return result;
    }
}


                                        MyScanner.java                                                                                      0000755 0023342 0001044 00000002124 10460220174 014112  0                                                                                                    ustar   it05077                         it05aic                         0000000 0000000                                                                                                                                                                        import java.io.*;
import java.util.*;

public class MyScanner
{
	private ArrayStack<Object> mTokenStack;

	public MyScanner(ArrayList<Object> tokenList) {
		calcUtil cU = new calcUtil();
		mTokenStack = new ArrayStack<Object>();
		for (int i = tokenList.size() - 1; i >= 0; --i) {
			if(tokenList.get(i) instanceof Double) {
			    Double Zahl = cU.roundTo6Digits((Double) tokenList.get(i));
				mTokenStack.push(Zahl);
			}else {
				mTokenStack.push(tokenList.get(i));	
			}
		}
		
	}
	
	public MyScanner(InputStream stream)
	{
		ArrayList<Object> tokenList = new ArrayList<Object>();
		System.out.println( "Enter arithmetic expression. " + 
							"Separate Operators with white space:");
		Scanner scanner = new Scanner(stream);
		while (scanner.hasNext()) {
			if (scanner.hasNextDouble()) {
				tokenList.add(scanner.nextDouble());
			} else {
				tokenList.add(scanner.next());
			}
		}
		mTokenStack = new ArrayStack<Object>();
		for (int i = tokenList.size() - 1; i >= 0; --i) {
			mTokenStack.push(tokenList.get(i));
		}
	}

	public ArrayStack<Object> getTokenStack() 
	{
		return mTokenStack;
	}
}                                                                                                                                                                                                                                                                                                                                                                                                                                            OutputGUI.java                                                                                      0000755 0023342 0001044 00000021656 10462326105 014076  0                                                                                                    ustar   it05077                         it05aic                         0000000 0000000                                                                                                                                                                        //Animation frame
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.applet.*;


public class OutputGUI extends JFrame {
	  //Declaration of objetct variables
	  private JFrame frame;
	  private JPanel ControlPanel;
	  private OutputPanel GraphicPanel;
	  private int mWidth = 800;
	  private int mHigh = 600;
	  private boolean nextStep = false;
	  int speed = 1;
	  
	  //initalize Sound File
	  File f=new File("Song.wav");
	  AudioClip sound = null;
	  	  
	//Constructor
	OutputGUI() {
		//Initialize Frame and Panel
		frame = new JFrame();
		ControlPanel = new JPanel();
		GraphicPanel = new OutputPanel();
		
		//Initialize actionListener
		ActionListener pressButton = new pressedButton();
		 
		//Initialize new Buttons
		JButton bRun = new JButton("Run");
		bRun.addActionListener(pressButton);
		
		JButton bStep = new JButton("Step");
		bStep.addActionListener(pressButton);
		 
		 
		//Initialize new Frame Attributes
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		GraphicPanel.setSize(mWidth,mHigh);
		GraphicPanel.setBackground(Color.yellow);
		ControlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 200,5));
		ControlPanel.add(bRun);
		ControlPanel.add(bStep);
		frame.add(ControlPanel, BorderLayout.NORTH);
		frame.add(GraphicPanel, BorderLayout.CENTER );
		frame.setBounds(200,50,800,600);
		frame.setVisible(false);
	}
	
	//Constructor to initialize window size 
	OutputGUI(int Width, int High) {
		this();
		frame.setSize(Width, High);

	}
	
	//Initialize InputList for graphical Output
	public void setInputList(Stack<Object>  InputList) {
		GraphicPanel.setInputList(InputList);
		//New Position for worker
		Point p = new Point();
		p = (Point) GraphicPanel.Eingabe.getLast().getPos().clone();
		p.x = calcUtil.relativXPosition(p.x,GraphicPanel.Eingabe.getLast());
		p.y -= 20;
		//Init worker
		GraphicPanel.worker = new workerElement(p);
		GraphicPanel.worker.work = false;

		GraphicPanel.repaint();
	}
	
	//deletes an element from InputList and add's it to the ArgumentList
	public void addArgumentfromInputList() {
		if( GraphicPanel.ArgumentList == null ){
			GraphicPanel.ArgumentList = new LinkedList<StringElement>();
		}
		
		StringElement str = GraphicPanel.Eingabe.removeFirst();
		
		GraphicPanel.ArgumentList.add(str);
		GraphicPanel.repaint();
		
		//Calculate new position 
		Point newPos = new Point(); 
		newPos.y = calcUtil.relativStackKoord(GraphicPanel.ArgListPos.y,
				                              GraphicPanel.ArgumentList);
		newPos.x = GraphicPanel.ArgListPos.x;
		
		moveElementToPos(str,newPos);
		
		if( !GraphicPanel.Eingabe.isEmpty() )
			moveInputList(str);
		
	}
	
	//Animate Elements
	private void moveElementToPos(Object o, Point pos) {
		GraphicElement gE = (GraphicElement) o;
		Point p = gE.getPos();
		while( !gE.getPos().equals(pos) ){
			gE.setPos(p);
			if(p.x>pos.x)
				p.x-=speed;
			if(p.x<pos.x)
				p.x+=speed;
			if(p.y>pos.y)
				p.y-=speed;
			if(p.y<pos.y)
				p.y+=speed;
			GraphicPanel.repaint();
			try{
				Thread.sleep(5);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//Initialize new results
	public void addErgebnis(Double Erg, boolean LHS) {
		Point ErgPos = new Point(GraphicPanel.ErgebnisPos.x,
								 GraphicPanel.ErgebnisPos.y);
		
		GraphicPanel.Ergebnis = new StringElement(Erg.toString(),ErgPos);
		GraphicPanel.repaint();
		
		if(GraphicPanel.Eingabe.isEmpty() &&
			GraphicPanel.OperantList.size() == 1) {
			Calculator.setStepMode(false);
		}
		
		nextStep = false;
		if(Calculator.getStepMode()) {
			while(!nextStep); 
			
			nextStep = false;
		}else {
			try{
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(GraphicPanel.Eingabe.size() > 0) {
			if(GraphicPanel.Eingabe.getFirst().getString().equals(")") &&
				GraphicPanel.OperantList.size() == 2 &&
				GraphicPanel.Eingabe.size() == 1) {
					
				GraphicPanel.Eingabe.removeLast();
				GraphicPanel.OperantList.removeFirst();
				GraphicPanel.repaint();
			}
		}
				
		if(!GraphicPanel.Eingabe.isEmpty() || 
		   GraphicPanel.OperantList.size() != 1) {
				addResultToArgList(LHS);
		}else {
			//stop sound
			//sound.stop();
			//happy worker
			GraphicPanel.worker.happy = true;
			while(true) {
				GraphicPanel.repaint();
				try {
					Thread.sleep(500);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
			
	}
	
	//Add result to ArgumentList
	public void addResultToArgList(boolean LHS) {
		if( GraphicPanel.ArgumentList == null ){
			GraphicPanel.ArgumentList = new LinkedList<StringElement>();
		}
		//Delete the calculated arguments and operants
		GraphicPanel.ArgumentList.removeLast();
		if( LHS )
			 GraphicPanel.ArgumentList.removeLast();
		GraphicPanel.OperantList.removeLast();
		
		//add result to ArgList as new Element
		StringElement str = GraphicPanel.Ergebnis;
		
		GraphicPanel.ArgumentList.add(str);
		GraphicPanel.Ergebnis = null;
		GraphicPanel.repaint();
		
		//Calculate new position 
		Point newPos = new Point(); 
		newPos.y = calcUtil.relativStackKoord(GraphicPanel.ArgListPos.y,
				                              GraphicPanel.ArgumentList);
		newPos.x = GraphicPanel.ArgListPos.x;
		
		moveElementToPos(str,newPos);
	}
	
	public void addOperantfromInputList(String operant) {
		if( GraphicPanel.OperantList == null ){
			GraphicPanel.OperantList = new LinkedList<StringElement>();
		}
		
		if( GraphicPanel.Eingabe.isEmpty() )
			return;
		
		StringElement str = GraphicPanel.Eingabe.removeFirst();
		
		GraphicPanel.OperantList.add(str);
		GraphicPanel.repaint();
		
		//Calculate new position 
		Point newPos = new Point(); 
		newPos.y = calcUtil.relativStackKoord(GraphicPanel.OpListPos.y,
				                              GraphicPanel.OperantList);
		newPos.x = GraphicPanel.OpListPos.x;
		
		moveElementToPos(str,newPos);
		
		if( !GraphicPanel.Eingabe.isEmpty() )
			moveInputList(str);
		
		//Klammern entfernen
		if( str.getString().equals(")")) {
			GraphicPanel.OperantList.removeLast();
			GraphicPanel.OperantList.removeLast();
		}
		GraphicPanel.repaint();
	}
	
	//Move InputList to left
	public void moveInputList(StringElement str) {
		int xDiff = calcUtil.relativXPosition(str.getPosX(),str) - str.getPosX();
		GraphicPanel.worker.work = true;
		for(int i=0; i<=xDiff;i++) {
			for(StringElement strE: GraphicPanel.Eingabe) {
				Point newP = strE.getPos();
				newP.x--; 
				strE.setPos(newP);
			}
			
			//New Position for worker
			if( !GraphicPanel.Eingabe.isEmpty() ) {
				Point p = new Point();
				p = (Point) GraphicPanel.Eingabe.getLast().getPos().clone();
				p.x = calcUtil.relativXPosition(p.x,GraphicPanel.Eingabe.getLast());
				p.y -= 20;
				GraphicPanel.worker.setPos(p);
			}
			
			GraphicPanel.repaint();
			try {
				Thread.sleep(10);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		GraphicPanel.worker.work = false;
	}
	
	//moves the next Argument which are to calculate
	public void moveArgumentToCalculate(boolean RHS) {
		Point newPos = new Point();
		newPos = (Point) GraphicPanel.ErgebnisPos.clone();
		newPos.y = calcUtil.relativStackKoord(GraphicPanel.ErgebnisPos.y,2);
		
		if ( RHS ) {
			newPos.x = calcUtil.relativXPosition(newPos.x,GraphicPanel.OperantList.getLast());
			newPos.y = calcUtil.relativStackKoord(GraphicPanel.ErgebnisPos.y,2);
			moveElementToPos(GraphicPanel.ArgumentList.getLast(),newPos);
		} else {
			
			int diff = calcUtil.relativXPosition(newPos.x,GraphicPanel.ArgumentList.get(
					GraphicPanel.ArgumentList.size()-2)) - GraphicPanel.ErgebnisPos.x;
			
			newPos.x -= diff;
			
			moveElementToPos(GraphicPanel.ArgumentList.get(GraphicPanel.ArgumentList.size()-2),newPos);
		}
		
	}
	
	public void moveOperantToCalculate() {			
		Point newPos = new Point();
		newPos = (Point) GraphicPanel.ErgebnisPos.clone();
		newPos.y = calcUtil.relativStackKoord(GraphicPanel.ErgebnisPos.y,2);
		moveElementToPos(GraphicPanel.OperantList.getLast(),newPos);
	}
	
	private class pressedButton implements ActionListener {
		
		public void actionPerformed(ActionEvent event) {
			String strPressedButton = event.getActionCommand();
			
			if( strPressedButton == "Run" ) {
				if(Calculator.getStepMode()) {
					Calculator.setStepMode(false);
				} else
					nextStep = true;
			}
			
			if( strPressedButton == "Step")
				if( !Calculator.getStepMode() ) {
					Calculator.setStepMode(true);
				}else
					nextStep = true;
		}
      
	}
		
	public void Visible(boolean visible) {
		frame.setVisible(visible);
		//start or stop sound
		if(visible) {
			try{
				sound = Applet.newAudioClip( f.toURL() );
			} catch (Exception e) {
				e.printStackTrace();
			}
			sound.loop();
		} else {
			sound.stop();
		}
	}

}
                                                                                  OutputPanel.java                                                                                    0000755 0023342 0001044 00000005437 10462326122 014507  0                                                                                                    ustar   it05077                         it05aic                         0000000 0000000                                                                                                                                                                        //Animation panel
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class OutputPanel extends JPanel {
	
	LinkedList<StringElement> Eingabe;
	LinkedList<StringElement> ArgumentList;
	LinkedList<StringElement> OperantList;
	StringElement Ergebnis = null;
	workerElement worker = null;
	
	final Point EingabeStartPos = new Point(300,30);
	final Point ErgebnisPos = new Point(390,300);
	final Point ArgListPos = new Point(200,500);
	final Point OpListPos = new Point(600,500);
	
	OutputPanel() {
		Eingabe = null;
		ArgumentList = null;
		OperantList = null;
	}
	
	//Initalize InputList
	public void setInputList(Stack<Object> InputList) {
		Point newPos = new Point();
		newPos = (Point) EingabeStartPos.clone();
		Eingabe = new LinkedList<StringElement>();
		Stack<Object> IList = new ArrayStack<Object>();
		try {
		  IList = InputList.clone();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		while(!IList.isEmpty()) {
			if( IList.top() instanceof Double) {
				Eingabe.add(new StringElement(Double.toString((Double) IList.top()), newPos));
				IList.pop();
				newPos.x = calcUtil.relativXPosition(newPos.x,
													 Eingabe.getLast());
			} else {
				Eingabe.add(new StringElement((String) IList.top(),newPos));
				IList.pop();
				newPos.x = calcUtil.relativXPosition(newPos.x,
													 Eingabe.getLast());
			}
		}
	}
	
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//Initialize Font
		g.setFont(new Font("Serif", Font.ITALIC, 18));
		
		StringElement str;
		g.setColor(Color.black);
		
		//Output from the Input
		g.drawString("Eingabeliste: ",EingabeStartPos.x - 125,EingabeStartPos.y);
		if( Eingabe != null ){
			for(int i = 0; i < Eingabe.size(); i++) {
				str = Eingabe.get(i);
				str.paintElement(g);
			}
		}
		
		//Output Worker
		if(worker != null)
			worker.paintElement(g);
		
		
		//Output from the ArgumentList
		g.setColor(Color.black);
		g.drawString("Argument - Stack",ArgListPos.x - 50,ArgListPos.y + 20);
		g.setColor(Color.blue);
		if( ArgumentList != null ) {
		   for(int i = 0; i < ArgumentList.size(); i++) {
			   str = ArgumentList.get(i);
			   str.paintElement(g);			   
		   }
		}
		
		//Output form the OperantList
		g.setColor(Color.black);
		g.drawString("Operant - Stack",OpListPos.x - 50,OpListPos.y + 20);
		g.setColor(Color.red);
		if( OperantList != null ) {
			for(int i = 0; i < OperantList.size(); i++) {
				   str = OperantList.get(i);
				   str.paintElement(g);			   
			   }
		}
		
		//Output from the Result
		g.setColor(new Color(10,180,10));
		if( Ergebnis != null ) {
			int yPos = calcUtil.relativStackKoord(ErgebnisPos.y,1);
			g.drawString("=",ErgebnisPos.x,yPos);
			Ergebnis.paintElement(g);
		}
				
	}

}

	                                                                                                                                                                                                                                 Stack.java                                                                                          0000755 0023342 0001044 00000001452 10460220174 013263  0                                                                                                    ustar   it05077                         it05aic                         0000000 0000000                                                                                                                                                                        public abstract class Stack<Element> implements Cloneable
{
    public abstract void    push(Element e);
    public abstract void    pop();
    public abstract Element top();
    public abstract boolean isEmpty();

    public Stack<Element> clone() throws CloneNotSupportedException {
        return (Stack<Element>) super.clone();
    }

    public final String toString() {
        Stack<Element> copy;
        try {
            copy = clone();
        } catch (CloneNotSupportedException e) {
            return "*** ERROR ***";
        }       
        return copy.convert();
    }

    private String convert() {
        if (isEmpty()) {
            return "|";
        } else {
            Element top = top();
            pop();
            return convert() + " " + top + " |";
        }
    }

}


                                                                                                                                                                                                                          StringElement.java                                                                                  0000755 0023342 0001044 00000001273 10460466444 015013  0                                                                                                    ustar   it05077                         it05aic                         0000000 0000000                                                                                                                                                                        import java.awt.*;

public class StringElement extends GraphicElement {
	
	private String StrElement;
	
	StringElement() {
		super();
		StrElement = new String("_");
	}
	
	StringElement(Point p) {
		super(p);
	}
	
	StringElement(String str) {
		super();
		StrElement = new String(str);
	}
	
	StringElement(String str, Point p) {
		super(p);
		StrElement = new String(str);
	}
	
	public void setString(String str) {
		StrElement = str;
	}
	
	public int getStrLength() {
		return StrElement.length();
	}
	
	public void paintElement(Graphics g) {
		g.drawString(StrElement,getPosX(),getPosY());				
	}
	
	public String getString() {
		return StrElement;
	}

}
                                                                                                                                                                                                                                                                                                                                     workerElement.java                                                                                  0000755 0023342 0001044 00000004412 10462325646 015054  0                                                                                                    ustar   it05077                         it05aic                         0000000 0000000                                                                                                                                                                        //wokerElement Class
//Animates the worker on the Top of the page

import java.awt.*;

public class workerElement extends GraphicElement{
	
	boolean work = false;
	boolean happy = false;
	
	private boolean up = true;
	
	private int Headsize = 6;
	private int Bodysize = 15;
	private int Legsize =  10;
	private int Armsize =  6;
	
	workerElement() {
		super();
	}
	
	workerElement(Point p) {
		super(p);
	}
	
	private void paintnormal(Graphics g) {
		g.setPaintMode();
		g.setColor(Color.black);
		//Kopf
		g.drawOval(getPosX()-3,getPosY(),Headsize,Headsize);
		//Body
		g.drawLine(getPosX(),getPosY()+ Headsize,getPosX(),getPosY()+Bodysize);
		//Legs
		g.drawLine(getPosX(),getPosY()+ Bodysize,getPosX()+4,getPosY()+Bodysize+Legsize);
		g.drawLine(getPosX(),getPosY()+ Bodysize,getPosX()-4,getPosY()+Bodysize+Legsize);
		//Arms
		if( work ) {
			g.drawLine(getPosX(),getPosY()+Headsize + 3,
					   getPosX()-Armsize,getPosY()+Headsize +3);
			g.drawLine(getPosX(),getPosY()+Headsize + 5,
					   getPosX()-Armsize,getPosY()+Headsize +5);
		} else {
			g.drawLine(getPosX(),getPosY()+Headsize +3,
					   getPosX()+Armsize,getPosY()+Headsize +5);
			g.drawLine(getPosX(),getPosY()+Headsize +3,
					   getPosX()-Armsize,getPosY()+Headsize +5);
		}
	}
	
	private void painthappy(Graphics g) {
		if(up) {
			g.setPaintMode();
			g.setColor(Color.black);
			//Kopf
			g.drawOval(getPosX()-3,getPosY(),Headsize,Headsize);
			//Body
			g.drawLine(getPosX(),getPosY()+ Headsize,getPosX(),getPosY()+Bodysize);
			//Legs
			//g.drawLine(getPosX(),getPosY()+ Bodysize,getPosX()+6,getPosY()+Bodysize+6);
			//g.drawLine(getPosX(),getPosY()+ Bodysize,getPosX()-6,getPosY()+Bodysize+6);
			g.drawLine(getPosX(),getPosY()+ Bodysize,getPosX()+4,getPosY()+Bodysize+Legsize);
			g.drawLine(getPosX(),getPosY()+ Bodysize,getPosX()-4,getPosY()+Bodysize+Legsize);
			
			//Arms
			g.drawLine(getPosX(),getPosY()+Headsize +5,
					   getPosX()-Armsize-2,getPosY()+Headsize -2);
			
			g.drawLine(getPosX(),getPosY()+Headsize +5,
					   getPosX()+Armsize+2,getPosY()+Headsize -2);
		}else {
			paintnormal(g);
		}
		up = !up ;		
	}
	
	
	public void paintElement(Graphics g) {
		if(happy) {
			painthappy(g);
		} else {
			paintnormal(g);
		}
	}

}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      