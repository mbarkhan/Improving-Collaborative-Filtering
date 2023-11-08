
package Proposed_Method;

import net.librec.conf.Configurable;

/**
 * Base class for things that may be configured with a {@link Configuration}.
 */
public abstract class Configured implements Configurable {

    public static final String CONF_DATA_COLUMN_FORMAT = "data.column.format";

    public static final String CONF_DFS_DATA_DIR = "dfs.data.dir";

    public static final String CONF_DATA_INPUT_PATH = "data.input.path";

    protected Configuration conf;

    /**
     * Construct a Configured.
     */
    public Configured() {
        this(null);
    }

    /**
     * Construct a Configured.
     *
     * @param conf  object for construction
     */
    public Configured(Configuration conf) {
        setConf(conf);
    }

    // inherit javadoc
    public void setConf(Configuration conf) {
        this.conf = conf;
    }

   
    @Override
    public void setConf(net.librec.conf.Configuration c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
