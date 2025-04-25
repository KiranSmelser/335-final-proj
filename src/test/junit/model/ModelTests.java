package model;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ CardTest.class, DeckTest.class, HandTest.class, CribTest.class, 
	EasyStrategyTest.class, HardStrategyTest.class, CribbageTest.class, BoardTest.class, 
	ComputerPlayerTest.class, PlayerTest.class })
public class ModelTests {
}
