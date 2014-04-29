package voice.objectTags;

import java.util.List;

import edu.cmu.sphinx.decoder.Decoder;
import edu.cmu.sphinx.decoder.ResultListener;
import edu.cmu.sphinx.decoder.search.SearchManager;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.PropertyException;
import edu.cmu.sphinx.util.props.PropertySheet;

public class LimitedDecoder extends Decoder {

    private int featureBlockSize;

    public LimitedDecoder() {
    	super();
    }

    @Override
    public void newProperties(PropertySheet ps) throws PropertyException {
        super.newProperties(ps);
        featureBlockSize = ps.getInt(PROP_FEATURE_BLOCK_SIZE);
    }

    /**
     *
     * @param searchManager
     * @param fireNonFinalResults
     * @param autoAllocate
     * @param resultListeners
     * @param featureBlockSize
     */
    public LimitedDecoder( SearchManager searchManager, boolean fireNonFinalResults, boolean autoAllocate, List<ResultListener> resultListeners, int featureBlockSize) {
        super( searchManager, fireNonFinalResults, autoAllocate, resultListeners, featureBlockSize);
        this.featureBlockSize = featureBlockSize;
    }
    
	@Override
    public Result decode(String referenceText) {
		searchManager.startRecognition();
        Result result;
        long time = System.currentTimeMillis();
        do {
            result = searchManager.recognize(featureBlockSize);
            if (result != null) {
                result.setReferenceText(referenceText);
                fireResultListeners(result);
            }
            if(System.currentTimeMillis() > (time + 10000)){
            	searchManager.stopRecognition();
            	return null;
            }
        } while (result != null && !result.isFinal());
        searchManager.stopRecognition();
        return result;
    }
}
