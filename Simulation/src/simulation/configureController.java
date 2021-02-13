package simulation;

import java.text.DecimalFormat;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.VBox;

public class configureController {
	

	@FXML private Label qLabel;
	@FXML private Slider qSlider;
	
	@FXML private Label pLabel;
	@FXML private Slider pSlider;
	
	@FXML private Spinner<Integer> seedSpinner;
	@FXML private Spinner<Integer> ticksSpinner; 
	@FXML private Spinner<Integer> ticksPerMinuteSpinner;
	@FXML private Spinner<Integer> elevatorCapacitySpinner;
	@FXML private Spinner<Integer> employeesSpinner;
	@FXML private Spinner<Integer> developersSpinner;
	@FXML private Spinner<Integer> floorsSpinner;
	@FXML private CheckBox completeInstantly;
	
	@FXML private VBox configure;
	

	/**
	 * constructs a new simulator with the user specified values.
	 */
	@FXML
	public void create() {
		configure.getScene().getWindow().hide();
		new Simulator(pSlider.getValue(), qSlider.getValue(), developersSpinner.getValue(), employeesSpinner.getValue(), ticksSpinner.getValue(), ticksPerMinuteSpinner.getValue(), 
				elevatorCapacitySpinner.getValue(), seedSpinner.getValue(), completeInstantly.isSelected(), floorsSpinner.getValue());
	}
	
	@FXML
	public void initialize() {
		
		//Listener p value.
		DecimalFormat df = new DecimalFormat("###.###");
		pSlider.valueProperty().addListener((property, oldvalue, newValue) -> {
			pLabel.setText("p: " + df.format(newValue));
	    }); 
		
		//Listener for q value.
		qSlider.valueProperty().addListener((property, oldvalue, newValue) -> {
			qLabel.setText("q: " + df.format(newValue));
	     });
	     
		
		//Spinner functionality
		Random rand = new Random(); 
		SpinnerValueFactory<Integer> seedSpinnerFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99999999, rand.nextInt(99999999));
		this.seedSpinner.setValueFactory(seedSpinnerFactory);
		seedSpinner.setEditable(true);

		//Number of ticks
		SpinnerValueFactory<Integer> ticksSpinnerFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99999999,2880);
		this.ticksSpinner.setValueFactory(ticksSpinnerFactory);
		ticksSpinner.setEditable(true);
		
		//Ticks per minute
		SpinnerValueFactory<Integer> ticksPerMinuteSpinnerFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 30);
		this.ticksPerMinuteSpinner.setValueFactory(ticksPerMinuteSpinnerFactory);
		ticksPerMinuteSpinner.setEditable(true);
		
		//Elevator capacity
		SpinnerValueFactory<Integer> elevatorCapacitySpinnerFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,30,4);
		this.elevatorCapacitySpinner.setValueFactory(elevatorCapacitySpinnerFactory);
		elevatorCapacitySpinner.setEditable(true);
	
		//Number of Employees
		SpinnerValueFactory<Integer> employeesSpinnerFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,20,10);
		this.employeesSpinner.setValueFactory(employeesSpinnerFactory);
		employeesSpinner.setEditable(true);
	
		//Number of Developers
		SpinnerValueFactory<Integer> developersSpinnerFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,20,10);
		this.developersSpinner.setValueFactory(developersSpinnerFactory);
		developersSpinner.setEditable(true);
		
		//floors
		SpinnerValueFactory<Integer> floorsSpinnerFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(7,20,7);
		this.floorsSpinner.setValueFactory(floorsSpinnerFactory);
		floorsSpinner.setEditable(true);
	}

}
