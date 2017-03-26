# Crossover

## Short Term Moving Average
STMA(i):= the average price of the stock over days i,i-1,i-2,...,i-59, so it is an average measured at day i over the most recent 60 days. This value is called Short term moving average.

## Long Term Moving Average
LTMA(i) := the average price of the stock over days i,i-1,i-2,...,i-299, so it is an average measured at day  over the most recent 300 days. This value is called Long term moving average.

Now, let's say that a crossover occurs at day i if and only if both STMA(i) and LTMA(i)  are defined at days i - 1 and i, and either one of the below conditions is fulfilled: 

* STMA(i - 1) > LTMA(i - 1) AND STMA(i) <= LTMA(i)