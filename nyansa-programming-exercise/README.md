
# Nyansa Programming Exercise

## Problem

[Link](https://sites.google.com/a/nyansa.com/nyansa-programming-exercise/).

You’re given an input file. Each line consists of a timestamp (unix epoch in seconds) and a url separated by ‘|’ (pipe operator). The entries are not in any chronological order. Your task is to produce a daily summarized report on url hit count, organized daily (mm/dd/yyyy GMT) with the earliest date appearing first. For each day, you should display the number of times each url is visited in the order of highest hit count to lowest count. Your program should take in one command line argument: input file name. The output should be printed to stdout. You can assume that the cardinality (i.e. number of distinct values) of hit count values and the number of days are much smaller than the number of unique URLs. You may also assume that number of unique URLs can fit in memory, but not necessarily the entire file.

input.txt
>1407564301|www.nba.com
1407478021|www.facebook.com
1407478022|www.facebook.com
1407481200|news.ycombinator.com
1407478028|www.google.com
1407564301|sports.yahoo.com
1407564300|www.cnn.com
1407564300|www.nba.com
1407564300|www.nba.com
1407564301|sports.yahoo.com
1407478022|www.google.com
1407648022|www.twitter.com

Output
>08/08/2014 GMT
www.facebook.com 2
www.google.com 2
news.ycombinator.com 1
08/09/2014 GMT
www.nba.com 3
sports.yahoo.com 2
www.cnn.com 1
08/10/2014 GMT
www.twitter.com 1

## Run

```bash
javac DailyUrlHitCount.java
java DailyUrlHitCount input.txt
```

## Complexity Analysis

### Time

N = number of records (lines).

Read each records from the input.txt: since we use TreeMap, the time complexity for insertion and lookup is O(logN).

Output the result: with the sorted data as key in our dataMap, during the output, we need to sort the number of hit for the urls within each date. We have a helper function ```softUrlsHit``` to take care of that. The time complexity is O(N*log(N)) for each sorted list.

Since we assume that the number of days are much smaller than the number of unique URLs, the entire time complexity will be O(logN) + O(N*log(N)) = O(N*log(N)).

### Space

O(N).
