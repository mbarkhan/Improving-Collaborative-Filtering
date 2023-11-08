
package Proposed_Method;

import com.google.common.collect.BiMap;
import net.librec.common.LibrecException;
import net.librec.data.DataAppender;
import net.librec.data.DataSplitter;
import net.librec.math.structure.DataSet;

public interface DataModel {
    /**
     * Build data model.
     *
     * @throws LibrecException
     *         if error occurs during building
     */
    public void buildDataModel() throws LibrecException;

    /**
     * Load data model.
     *
     * @throws LibrecException
     *         if error occurs during loading
     */
    public void loadDataModel() throws LibrecException;

    /**
     * Save data model.
     *
     * @throws LibrecException
     *         if error occurs during saving
     */
    public void saveDataModel() throws LibrecException;

    /**
     * Get data splitter.
     *
     * @return  the splitter of data model.
     */
    public DataSplitter getDataSplitter();

    /**
     * Get train data set.
     *
     * @return  the train data set of data model.
     */
    public DataSet getTrainDataSet();

    /**
     * Get test data set.
     *
     * @return  the test data set of data model.
     */
    public DataSet getTestDataSet();

    /**
     * Get valid data set.
     *
     * @return  the valid data set of data model.
     */
    public DataSet getValidDataSet();

    /**
     * Get datetime data set.
     *
     * @return  the datetime data set of data model.
     */
    public DataSet getDatetimeDataSet();

    /**
     * Get user mapping data.
     *
     * @return  the user {raw id, inner id} map of data model.
     */
    public BiMap<String, Integer> getUserMappingData();

    /**
     * Get item mapping data.
     *
     * @return  the item {raw id, inner id} map of data model.
     */
    public BiMap<String, Integer> getItemMappingData();

    /**
     * Get data appender.
     *
     * @return  the appender of data model.
     */
    public DataAppender getDataAppender();
    

    /**
     * Get data Context.
     *
     * @return  the appender of data model.
     */
    public DataContext getContext();
    
}
