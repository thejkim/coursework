###############################     INSTALLATION/PREP     ################
# This is a code template for logistic regression using stochastic gradient ascent to be completed by you 
# in CSI 431/531 @ UAlbany
#


###############################     IMPORTS     ##########################
import numpy as np
import pandas as pd
import math as mt
from numpy import linalg as li
import matplotlib.pyplot as plt


###############################     FUNCTION DEFINITOPNS   ##########################

"""
Receives data point x and coefficient parameter values w 
Returns the predicted label yhat, according to logistic regression.
"""
def predict(x, w):
    # Binary Logistic Regression
    s = np.dot(w.T, x) # dot product of w.Transpose and data point x 
    y_hat = 1/(1+np.exp(-s))

    if y_hat >= 0.5:
        y_prediction = 1
    else:
        y_prediction = 0

    return(y_prediction)  
    
"""
Receives data point (x), data label (y), and coefficient parameter values (w) 
Computes and returns the gradient of negative log likelihood at point (x)
"""
def gradient(x, y, w):
    # The point-specific gradient
    #m=x.shape[1]
    s=(np.dot(w.T,x))
    y_hat = 1/(1+np.exp(-s))
    grad = (y-y_hat)*x
    return(grad)

"""
Receives the predicted labels (y_hat), and the actual data labels (y)
Computes and returns the cross-entropy loss
"""
def cross_entropy(y_hat, y):
    # Binary Cross-Entropy Error
    cross_ent = (-np.sum(y*np.log(y_hat)+(1-y)*np.log(1-y_hat)))
    return(cross_ent)

"""
Receives data set (X), dataset labels (y), learning rate (step size) (psi), stopping criterion (for change in norm of ws) (epsilon), and maximum number of epochs (max_epochs)
Computes and returns the vector of coefficient parameters (w), and the list of cross-entropy losses for all epochs (cross_ent)
"""
def logisticRegression_SGA(X, y, psi, epsilon, max_epochs):
    # print("print X")
    # print(X)
    """
      TODO
      NOTE: remember to either shuffle the data points or select data points randomly in each internal iteration.
      NOTE: stopping criterion: stop iterating if norm of change in w (norm(w-w_old)) is less than epsilon, or the number of epochs (iterations over the whole dataset) is more than maximum number of epochs
    """ 
    X = X.T
    # shuffle the rows of dataset X
    np.random.shuffle(X) # shuffle the array along the first axis of the multi-dimensional array X. The order of sub-arrays is changed but their contents remain the same.
    r = len(X.axes[0]) # number of rows
    c = len(X.axes[1]) # number of columns
    t = 0 # step.iteration counter
    w = np.array([0 for i in range(c)]) # initial weight vector

    #w = w.shape
    normInW = 0 # norm(w-w_old)
    cross_ent = []
    while normInW<epsilon and t<max_epochs:
        w_copy = w[t] # make a copy of w
        for row in X.itertuples(index=False): # iterate over rows. Note that X is now in random order
            arg = row # as we must explicitly pass arg to a function. Otherwise, correct values will not be passed to.
            y_hat = predict(arg, w_copy)
            cross_ent.append(cross_entropy(y_hat, y)) # cross-entropy loss
            grad = gradient(row, y, w_copy) # compute gradient at xi
            w_copy = w_copy+psi*grad # update estimate for w
        w[t+1] = w_copy
        t = t+1
        if(t>0):
            normInW = li.norm(w-w[t-1])

    return(w,cross_ent)    
  
  
if __name__ == '__main__':  
    ## initializations and config
    psi=0.1 # learning rate or step size
    epsilon = 10 # used in SGA's stopping criterion to define the minimum change in norm of w
    max_epochs = 8 # used in SGA's stopping criterion to define the maximum number of epochs (iterations) over the whole dataset
    
    ## loading the data
    df_train = pd.read_csv("cancer-data-train.csv", header=None)
    df_test = pd.read_csv("cancer-data-test.csv", header=None)
    
    ## split into features and labels
    X_train, y_train = df_train.iloc[:, :-1], df_train.iloc[:, -1].astype("category").cat.codes #Convert string labels to numeric
    X_test, y_test = df_test.iloc[:, :-1], df_test.iloc[:, -1].astype("category").cat.codes
    
    ## augmenting train data with 1 (X0)
    X_train.insert(0,'',1)
    X_test.insert(0,'',1)

    ## learning logistic regression parameters
    [w, cross_ent] = logisticRegression_SGA(X_train, y_train, psi, epsilon, max_epochs)
    
    # plotting the cross-entropy across epochs to see how it changes
    plt.plot(cross_ent, 'o', color='black');
    
    
    """
      TODO: calculate and print the average cross-entropy error for training data (cross-entropy error/number of data points)
    """
    print("Average cross-entropy error for dataset X_train: ")
    sumOfError = 0
    for i in cross_ent:
        sumOfError = sumOfError + i
    avgError_train = sumOfError/len(X_train.axes[0])
    print(avgError_train)

    """
      TODO: predict the labels for test data points using the learned w's
    """
    print("predicted label for dataset X_test: ")
    for row in X_test.itertuples(index=False):
        arg = row
        y_hat = predict(arg, w)
        print(y_hat)
  
  
