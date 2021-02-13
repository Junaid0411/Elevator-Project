module simulation {
	// We need these modules from JavaFX
	requires javafx.controls;
	requires javafx.fxml;
	requires transitive javafx.graphics;
	requires junit;

	// We have to export our own packages so JavaFX can access them
	exports people;
	exports simulation;

	opens simulation to javafx.fxml;
}