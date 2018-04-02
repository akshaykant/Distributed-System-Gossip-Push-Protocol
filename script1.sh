#mvn compile  
#mvn exec:java -Dexec.mainClass="uk.ac.ed.ds.kant.DistributedSystems.TestA" -Dexec.args="complete_graph 1"

if [ "$1" = "compile" ]
then
    mvn compile
elif [ "$1" = "q1" ]
then
    mvn exec:java -Dexec.mainClass="uk.ac.ed.ds.kant.DistributedSystems.TestA" -Dexec.args="$1 $2 $3"
    
elif [ "$1" = "q2" ]
then
    mvn exec:java -Dexec.mainClass="uk.ac.ed.ds.kant.DistributedSystems.TestA" -Dexec.args="$1 $2 $3"
elif [ "$1" = "q3" ]
then
    mvn exec:java -Dexec.mainClass="uk.ac.ed.ds.kant.DistributedSystems.TestA" -Dexec.args="$1 $2 $3"
fi
      


