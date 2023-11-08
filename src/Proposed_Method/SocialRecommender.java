
package Proposed_Method;

import net.librec.common.LibrecException;
import net.librec.data.convertor.appender.SocialDataAppender;
import net.librec.math.algorithm.Maths;
import net.librec.math.structure.SparseMatrix;
import net.librec.recommender.MatrixFactorizationRecommender;


public abstract class SocialRecommender extends MatrixFactorizationRecommender {
    /**
     * socialMatrix: social rate matrix, indicating a user is connecting to a number of other users
     */
    protected SparseMatrix socialMatrix;

    /**
     * social regularization
     */
    protected float regSocial;

    @Override
    public void setup() throws LibrecException {
        super.setup();
        regSocial = conf.getFloat("rec.social.regularization", 0.01f);
        // social path for the socialMatrix
        socialMatrix = ((SocialDataAppender) getDataModel().getDataAppender()).getUserAppender();
    }

    @Override
    protected double predict(int userIdx, int itemIdx, boolean bounded) throws LibrecException {
        double predictRating = predict(userIdx, itemIdx);

        if (bounded)
            return denormalize(Maths.logistic(predictRating));

        return predictRating;
    }

    /**
     * denormalize a prediction to the region (minRate, maxRate)
     *
     * @param predictRating a prediction to the region (minRate, maxRate)
     * @return  a denormalized prediction to the region (minRate, maxRate)
     */
    protected double denormalize(double predictRating) {
        return minRate + predictRating * (maxRate - minRate);
    }

    /**
     * normalize a rating to the region (0, 1)
     *
     * @param rating a given rating
     * @return  a normalized rating
     */
    protected double normalize(double rating) {
        return (rating - minRate) / (maxRate - minRate);
    }
}

