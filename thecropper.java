package Cropper;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.awt.*;
import javax.swing.*;
import javax.imageio.*;


public class thecropper extends JFrame implements ActionListener, MouseListener, MouseMotionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel text = new JLabel ("", JLabel.CENTER);
	JPanel controller1 = new JPanel();
	JTextField numberofimages = new JTextField(10);
	JButton submit = new JButton("Start");
	String namingconvention = ""; // jak nazywaæ
	JLabel image = new JLabel(new ImageIcon());
	JScrollPane kolko = new JScrollPane(image);
	JButton brightnessdown = new JButton("<<Brightness");
	JButton brightnessup = new JButton("Brightness>>");
	JButton contrastdown = new JButton("<<Contrast");
	JButton contrastup = new JButton("Contrast>>");
	JButton tintdown = new JButton("<<Tint");
	JButton tintup = new JButton("Tint>>");
	JButton whitebalance = new JButton("White Balance");
	JButton invert = new JButton("Invert Colors");
	JButton imdone = new JButton("I'm done with this image");
	JButton quit = new JButton("Exit");
	Image currentimage;
	int i = 0;
	int m = 0;
	int imnum = 1;
	imageeditor edito = new imageeditor();
	JFrame control;
	Image []pics = new Image [40];
	int x1, y1, x2, y2;
	boolean cropping = false;
	
	
	public thecropper()
	{
		setSize(800, 300);
		setTitle("Image cropper");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new GridLayout(3, 1));
		submit.addActionListener(this);
		controller1.removeAll();
		controller1.add(submit); 
		submit.setPreferredSize(new Dimension(100, 60));
		text.setText("Welcome to image cropper Simon. It will process up to 40 images from input folder. Set naming convention for the images and press start.");
		add(text, BorderLayout.CENTER);
		add(numberofimages);
		add(controller1);
		setVisible(true);

		
	}

	

	
	public void actionPerformed(ActionEvent e) //add responsiveness to buttons
	{
		if (e.getSource() == submit) //check which button was clicked
		{
				namingconvention = numberofimages.getText();
				imageprocessor();
		}
		
		if (e.getSource() == brightnessdown)
		{
			brightnessdown();
		}
		
		if (e.getSource() == brightnessup)
		{
			brightnessup();
		}
		
		if (e.getSource() == contrastdown)
		{
			contrastdown();
		}
		
		if (e.getSource() == contrastup)
		{
			contrastup();
		}
		
		if (e.getSource() == tintdown)
		{
			tintdown();
		}
		
		if (e.getSource() == tintup)
		{
			tintup();
		}
		
		if (e.getSource() == whitebalance)
		{
			whitebalance();
		}
		
		if (e.getSource() == invert)
		{
			invert();
		}
		
		if (e.getSource() == imdone)
		{
			imdone();
		}
		
		if (e.getSource() == quit)
		{
			quit();
		}
	}
	
	public void imageprocessor () 
	{
		String path = new File("").getAbsolutePath();//get absolute location of the Image cropper file
		File folder = new File(path.concat("\\Input folder")); //get absolute location of input folder
		System.out.println(path.concat("\\Input folder"));
		File[] listOfFiles = folder.listFiles(); //read images and save them to the temporary array

		for (File file : listOfFiles) //for all files in the table
		{
			try
			{
				if (ImageIO.read(file) != null) //if file is an image
				{
					try
					{
						pics [i] = ImageIO.read(file); //save it to array and increment counter
						i++; 
					}
					catch (Exception w)
					{
						;
					}
					
				}
			} 
			catch (IOException e)
			{
				continue;
			}
		}
		
		currentimage = pics[m];
		BufferedImage pic = (BufferedImage) currentimage;
		edito = new imageeditor(currentimage.getScaledInstance(pic.getWidth()/4, pic.getHeight()/4, Image.SCALE_DEFAULT)); // scaling image so that it fits
		edito.setVisible(true);
		edito.image.addMouseListener(this); //add responsiveness to mouse movements
		edito.image.addMouseMotionListener(this);
		control = imageeditorcontrols();
		control.setVisible(true);
		
	}
	
	public JFrame imageeditorcontrols() //setting up controls for image
	{
		JFrame controls = new JFrame();
		controls.setSize(300, 300);
		setTitle("Image editor controls");
		controls.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		controls.setLayout(new GridLayout(5, 2)); //set layout of this frame
		
		controls.add(brightnessdown); // add button
		brightnessdown.addActionListener(this); //add responsiveness to the button
		controls.add(brightnessup);
		brightnessup.addActionListener(this);
		controls.add(contrastdown);
		contrastdown.addActionListener(this);
		controls.add(contrastup);
		contrastup.addActionListener(this);
		controls.add(tintdown);
		tintdown.addActionListener(this);
		controls.add(tintup);
		tintup.addActionListener(this);
		controls.add(whitebalance);
		whitebalance.addActionListener(this);
		controls.add(invert);
		invert.addActionListener(this);
		controls.add(imdone);
		imdone.addActionListener(this);
		controls.add(quit);
		quit.addActionListener(this);
		
		return controls;
	}
	
	public void brightnessdown() 
	{
        BufferedImage pic = (BufferedImage) currentimage;
		for (int x = 0; x < pic.getWidth(); x++) { //for every column
            for (int y = 0; y < pic.getHeight(); y++) { //for every row
                int rgba = pic.getRGB(x, y); //save color of a pixel into temporary int value
                Color col = new Color(rgba, true); //save color of a pixel as color variable
                float [] colorsconverted = Color.RGBtoHSB(col.getRed(), col.getGreen(), col.getBlue(), null); //convert into HSB color space
                colorsconverted[2] -= 0.05; //decrease brightness, which is third value in the table
                rgba = Color.HSBtoRGB(colorsconverted [0], colorsconverted [1], colorsconverted [2]); //convert back to RGB color space
                col = new Color (rgba, true); //save new color into color variable
                pic.setRGB(x, y, col.getRGB()); //change color of a pixel
            }
        }
		edito.dispose(); //close preview
		currentimage=(Image) pic; //save edited image
		viewer();
	}
	
	public void brightnessup()
	{
        BufferedImage pic = (BufferedImage) currentimage;
		for (int x = 0; x < pic.getWidth(); x++) {
            for (int y = 0; y < pic.getHeight(); y++) {
                int rgba = pic.getRGB(x, y);
                Color col = new Color(rgba, true);
                float [] colorsconverted = Color.RGBtoHSB(col.getRed(), col.getGreen(), col.getBlue(), null);
                colorsconverted[2] += 0.05;
                rgba = Color.HSBtoRGB(colorsconverted [0], colorsconverted [1], colorsconverted [2]);
                col = new Color (rgba, true);
                pic.setRGB(x, y, col.getRGB());
            }
        }
		edito.dispose();
		currentimage=(Image) pic;
		viewer();
	}
	
	public void contrastdown()
	{
		BufferedImage pic = (BufferedImage) currentimage;
		RescaleOp contrast; //object for changing contrast
		float con = 0.9f; //scale factor is lower, as contrast should decrease
		contrast = new RescaleOp(con, 1.0f, null); //set rescaleop object
		pic = contrast.filter(pic, pic); //apply rescaleop to picture
		edito.dispose();
		currentimage=(Image) pic;
		viewer();
	}
	
	public void contrastup()
	{
		BufferedImage pic = (BufferedImage) currentimage;
		RescaleOp contrast;//object for changing contrast
		float con = 1.1f; //scale factor is higher, as contrast should increase
		contrast = new RescaleOp(con, 1.0f, null); //set rescaleop object
		pic = contrast.filter(pic, pic); //apply rescaleop to picture
		edito.dispose();
		currentimage=(Image) pic;
		viewer();
	}
	
	public void tintdown() //tint is otherwise known as hue, so each pixel is converted to HSB color scale and hue value is increased
	{
        BufferedImage pic = (BufferedImage) currentimage;
		for (int x = 0; x < pic.getWidth(); x++) { //for every column
            for (int y = 0; y < pic.getHeight(); y++) { //for every row
                int rgba = pic.getRGB(x, y);
                Color col = new Color(rgba, true);
                float [] colorsconverted = Color.RGBtoHSB(col.getRed(), col.getGreen(), col.getBlue(), null);
                colorsconverted[0] -= 0.05;
                rgba = Color.HSBtoRGB(colorsconverted [0], colorsconverted [1], colorsconverted [2]);
                col = new Color (rgba, true);
                pic.setRGB(x, y, col.getRGB());
            }
        }
		edito.dispose();
		currentimage=(Image) pic;
		viewer();
	}
	
	public void tintup()
	{
        BufferedImage pic = (BufferedImage) currentimage;
		for (int x = 0; x < pic.getWidth(); x++) { //for every column
            for (int y = 0; y < pic.getHeight(); y++) { //for every row
                int rgba = pic.getRGB(x, y); //save value of color for a pixel
                Color col = new Color(rgba, true); //convert it into color variable
                float [] colorsconverted = Color.RGBtoHSB(col.getRed(), col.getGreen(), col.getBlue(), null);
                colorsconverted[0] += 0.05; //convert into hsb colorspace and increase first value (hue)
                rgba = Color.HSBtoRGB(colorsconverted [0], colorsconverted [1], colorsconverted [2]);
                col = new Color (rgba, true); //convert back to rgb and save new color
                pic.setRGB(x, y, col.getRGB()); //change color of a pixel
            }
        }
		edito.dispose(); //close preview
		currentimage=(Image) pic; //save edited image
		viewer();
	}
	
	public void whitebalance() 
	{
		BufferedImage pic = (BufferedImage) currentimage;
		int widthbrightest = 0;
		int heightbrightest = 0;
		double brightnessbrightest = 0.0d;
		double brightnesscurrent = 0.0d;
		for (int x = 0; x < pic.getWidth(); x++)  // find brightest pixel
		{
            for (int y = 0; y < pic.getHeight(); y++)
            {
                int rgba = pic.getRGB(x, y); //save color of a pixel
                Color col = new Color(rgba, true); //save it as color variable
                brightnesscurrent = col.getRed()*0.2126 + col.getGreen()*0.7152 + col.getBlue()*0.0722; //calculate its brightness
                
                if (brightnesscurrent > brightnessbrightest) //if it is brighter than current brightest
                {
                	brightnessbrightest = brightnesscurrent; //save new pixel as brightest
                	widthbrightest = x;
                	heightbrightest = y;
                }
            }
        }	
		Color brightest = new Color(pic.getRGB(widthbrightest, heightbrightest), true);
		double red = 255.0/brightest.getRed(); //set up scale factors for each color
		double green = 255.0/brightest.getGreen();
		double blue = 255.0/brightest.getBlue();
		
		for (int x = 0; x < pic.getWidth(); x++)  // convert all pixels
		{
			
            for (int y = 0; y < pic.getHeight(); y++)
            {
                int rgba = pic.getRGB(x, y);
                Color col = new Color(rgba, true);
                int nr = (int)(col.getRed()*red+0.5); //round to nearest integer
                int ng = (int)(col.getGreen()*green+0.5);
                int nb = (int)(col.getBlue()*blue+0.5);
                if (nr > 255)
                	nr = 255; //prevent invalid data (colors have values 0-255)
                if (ng > 255)
                	ng = 255;
                if (nb > 255)
                	nb = 255;
                col = new Color(nr, ng, nb); //save new color
                pic.setRGB(x, y, col.getRGB()); //change pixel of the image
            }
        }
		edito.dispose();
		currentimage=(Image) pic;
		viewer();
	}
	
	public void invert ()
	{
        BufferedImage pic = (BufferedImage) currentimage;
		for (int x = 0; x < pic.getWidth(); x++) { //for every column
            for (int y = 0; y < pic.getHeight(); y++) { //for every row
                int rgba = pic.getRGB(x, y); //save color of the pixel
                Color col = new Color(rgba, true);
                col = new Color(255 - col.getRed(), 255 - col.getGreen(), 255 - col.getBlue()); //reverse it
                pic.setRGB(x, y, col.getRGB()); //change pixel of the image
            }
        }
		edito.dispose();
		currentimage=(Image) pic;
		viewer();
	}
	
	public void imdone()
	{
		m++;
		
		if (m==i)
		{
			this.dispose();
			control.dispose();
			edito.dispose();
		}
		else
		{
			edito.dispose();
			currentimage=pics[m];
			viewer();
		}
	}
	
	public void quit()
	{
		this.dispose();
		control.dispose();
		edito.dispose();
	}
	
	public void viewer() // smaller window to make editing easier
	{
		BufferedImage pic = (BufferedImage) currentimage;
		Image view = currentimage.getScaledInstance(pic.getWidth()/4, pic.getHeight()/4, Image.SCALE_DEFAULT); //scale the image
		edito = new imageeditor(view);
		edito.image.addMouseListener(this); //add responsiveness to mouse actions
		edito.image.addMouseMotionListener(this);
		control.setVisible(false);
		control.setVisible(true);
	}
	
	public void saving(BufferedImage pic) throws IOException //reads inputs and adjusts it to original input
	{
		String path = new File("").getAbsolutePath(); //get absolute location of image cropper file
		File savinglocation = new File(path.concat("\\Output folder")); //get absolute location of input folder
		String name = namingconvention + imnum +".jpg"; //set up name for new image
		File outputfile = new File(savinglocation, name); //set up file for saving
		int leftx, topy, rightx, bottomy;
		if (x1 > x2) //find the top left corner coordinates (x variable)
		{
			leftx = x2*4; //rescale back the input (it was done on scaled down image, so has to be scaled up)
			rightx = x1*4;
		}
		else 
		{
			leftx = x1*4;
			rightx = x2*4;
		}
		
		if (y1 > y2) //find the top left corner coordinates (y variable)
		{
			topy = y2*4;
			bottomy = y1*4;
		}
		else
		{
			topy = y1*4;
			bottomy = y2*4;
		}
		
		BufferedImage croped = pic.getSubimage(leftx, topy, (rightx-leftx), (bottomy-topy)); //crop the image
		ImageIO.write(croped, "jpg", outputfile); //save the image
		imnum++; //increment counter of images
	}



	@Override
	public void mousePressed(MouseEvent e)
	{
		Point p = MouseInfo.getPointerInfo().getLocation(); //get location of a pointer
		x1= p.x; //save location of a pointer
		y1 = p.y;
		
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		if (cropping == true) //if user has been attempting to crop the image
		{
			Point p = MouseInfo.getPointerInfo().getLocation(); //get location of a pointer
			x2= p.x; //save location of a pointer
			y2 = p.y;
			cropping = false; // reseting the variable
			try
			{
				saving((BufferedImage) currentimage); //attempt saving of new image
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}
		
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		cropping = true;	//inform that image is going to be cropped
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{	
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
	}
	
	public static void main(String[] args)
	{
		thecropper window = new thecropper();	
	}

}