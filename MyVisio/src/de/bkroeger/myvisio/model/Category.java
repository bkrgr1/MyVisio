package de.bkroeger.myvisio.model;

import java.util.ArrayList;
import java.util.List;

import de.bkroeger.myvisio.model.AbstractModel;

public class Category extends AbstractModel {
	
	private String name;
	public  String getName() { return this.name; }
	
	private List<ShapeSet> shapeSets = new ArrayList<ShapeSet>();
	public void addShapeSet(ShapeSet shapeSet) {
		shapeSets.add(shapeSet);
	}
	
	private List<ExampleWorkbook> examples = new ArrayList<ExampleWorkbook>();
	public void addExample(ExampleWorkbook example) {
		examples.add(example);
	}
	public List<ExampleWorkbook> getExamples() {
		return examples;
	}

	public Category(String name) {
		this.name = name;
	}
	
	
	public String toString() {
		return this.name;
	}
}
