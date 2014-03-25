package main;

import static org.junit.Assert.*;

import javax.speech.EngineException;
import javax.speech.EngineStateError;
import javax.speech.recognition.GrammarException;
import javax.speech.recognition.RuleGrammar;
import javax.speech.recognition.RuleParse;
import javax.swing.text.html.HTMLEditorKit.Parser;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.sun.speech.engine.recognition.BaseRecognizer;
import com.sun.speech.engine.recognition.BaseRuleGrammar;

import edu.cmu.sphinx.jsgf.JSGFGrammar;
import edu.cmu.sphinx.util.props.ConfigurationManager;

public class TestGrammar {

	String[] sentences = {"Turn on lamp", "lamp", "disable the kitchen lights", "kitchen"};
	String[] expected = {"Turn on lamp", null, "disable the kitchen lights", null};
	ConfigurationManager cm;
	BaseRecognizer recognizer;
	RuleGrammar grammar;
	JSGFGrammar j;

	@Before
	public void beforeTest(){
		cm = new ConfigurationManager(Jarvis.class.getResource("jarvis.config.xml"));
		j = (JSGFGrammar) cm.lookup("jsgfGrammar");
		recognizer = new BaseRecognizer(j.getGrammarManager());
		System.out.println(j.getGrammarManager());
		try {
			recognizer.allocate();
		} catch (EngineException | EngineStateError e) {
			e.printStackTrace();
		}
		grammar = new BaseRuleGrammar(recognizer, j.getRuleGrammar());
	}

	@Test
	public void test() {
		RuleParse parser = null;
		for(String s: sentences){
			try {
				parser = grammar.parse(s, null);
			} catch (GrammarException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(String e: expected){
				assertTrue(parser.toString().equalsIgnoreCase(e));
			}
		}
	}
}
