`log(x) < 0 if x < 1`

所以，等宽离散化后，分的样本越平均，代码中的熵越小。

```
/**
 * Optimizes the number of bins using leave-one-out cross-validation.
 * 
 * @param index the attribute index
 */
protected void findNumBins(int index) {

    double min = Double.MAX_VALUE, max = -Double.MAX_VALUE, binWidth = 0, entropy, bestEntropy = Double.MAX_VALUE, currentVal;
    double[] distribution;
    int bestNumBins = 1;
    Instance currentInstance;

    // Find minimum and maximum
    for (int i = 0; i < getInputFormat().numInstances(); i++) {
        currentInstance = getInputFormat().instance(i);
        if (!currentInstance.isMissing(index)) {
            currentVal = currentInstance.value(index);
            if (currentVal > max) {
                max = currentVal;
            }
            if (currentVal < min) {
                min = currentVal;
            }
        }
    }

    // Find best number of bins
    for (int i = 0; i < m_NumBins; i++) {
        distribution = new double[i + 1];
        binWidth = (max - min) / (i + 1);

        // Compute distribution
        for (int j = 0; j < getInputFormat().numInstances(); j++) {
        currentInstance = getInputFormat().instance(j);
        if (!currentInstance.isMissing(index)) {
            for (int k = 0; k < i + 1; k++) {
                if (currentInstance.value(index) <= (min + (((double) k + 1) * binWidth))) {
                    distribution[k] += currentInstance.weight();
                    break;
                    }
                }
            }
        }

        // Compute cross-validated entropy
        entropy = 0;
        for (int k = 0; k < i + 1; k++) {
            if (distribution[k] < 2) {
                entropy = Double.MAX_VALUE;
                break;
            }
            entropy -= distribution[k] * Math.log((distribution[k] - 1) / binWidth);
        }

        // Best entropy so far?
        if (entropy < bestEntropy) {
            bestEntropy = entropy;
            bestNumBins = i + 1;
        }
    }

    // Compute cut points
    double[] cutPoints = null;
    if ((bestNumBins > 1) && (binWidth > 0)) {
        cutPoints = new double[bestNumBins - 1];
        for (int i = 1; i < bestNumBins; i++) {
            cutPoints[i - 1] = min + binWidth * i;
        }
    }
    m_CutPoints[index] = cutPoints;
}