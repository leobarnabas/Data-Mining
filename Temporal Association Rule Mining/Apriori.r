library("arules");
library("arulesViz");

script.dir <- dirname(sys.frame(1)$ofile);
setwd(script.dir);
txn = read.transactions(file="nodefailure_cleaned.txt", format="basket",sep=",");

# plotting the frequencies
itemFrequencyPlot(txn, topN = 20)

# apriori computation
rules=apriori(txn, parameter = list(target="rules",support=0.5, confidence=0.7, minlen=2, maxlen=6));
inspect(rules);
plot(rules);

#Plot graph-based visualisation:

subrules2 <- head(sort(rules, by="lift"), 10)

plot(subrules2, method="graph",control=list(type="items",main=""))

