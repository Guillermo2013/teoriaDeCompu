import com.sun.javafx.collections.MappingChange
import org.jetbrains.annotations.Mutable
import java.util.*

/**
 * Created by user02 on 26/08/2016.
 */
 class DFAMinimizator :Automatas(mutableListOf(), mutableListOf(),Estado("",false), mutableListOf())  {

    fun minimize(dfa:DFA):DFA {
        var stateSetMapping : MutableMap<Estado, Set<Estado>>  = mutableMapOf()

        var sets = partition(dfa, stateSetMapping);

        return createMinimizedAutomaton(dfa, stateSetMapping, sets as MutableSet<MutableSet<Estado>>);
    }
    fun  partition(automaton :DFA ,stateSetMapping:Map<Estado, Set<Estado>>):Set<Set<Estado>> {

         var sets = initSets(automaton, stateSetMapping as MutableMap<Estado, Set<Estado>>)
        var partition:Set<Set<Estado>> ?= null

        while(!sets.equals(partition)) {
            partition = sets;
            sets = LinkedHashSet<Set<Estado>>();

            for( set in partition) {
                split(automaton, set as MutableSet<Estado>, stateSetMapping , sets as MutableSet<MutableSet<Estado>>);
            }
        }
        return sets;
    }



    fun  initSets( automaton:DFA, stateSetMapping:MutableMap<Estado, Set<Estado>> ):Set<Set<Estado>> {
        var sets =  LinkedHashSet<Set<Estado>>();
        var finalStates =  LinkedHashSet<Estado>();
        var nonFinalStates = LinkedHashSet<Estado>();
        for( state in automaton.estados) {
        var set = if(state.EsAcceptable)  finalStates else nonFinalStates;

        set.add(state);
        stateSetMapping.put(state, set);
    }

        sets.add(finalStates);
        sets.add(nonFinalStates);

        return sets;
    }



    fun split(automaton:DFA , set:MutableSet<Estado>,stateSetMapping:MutableMap<Estado, Set<Estado>>, sets:MutableSet<MutableSet<Estado>>) {
        var firstSet:Set<Estado>? = null;
        var secondSet :Set<Estado>?= null;
        var splitted = false;
        for(c in automaton.alfabeto) {
        firstSet =  LinkedHashSet<Estado>();
        secondSet = LinkedHashSet<Estado>();
        var firstToSet:Set<Estado> ? = null;
        var first = true;
        for(state  in set) {
        if(state !=null) {
            var toState: Estado = automaton.SearchDestiny(state, c) as Estado
            var toSet:Set<Estado>? = if(toState != null ) stateSetMapping.get(toState) else null
            if(first) {
                firstToSet = toSet as Set<Estado>;
                firstSet.add(state);
                first = false;
            } else if(firstToSet == null && toSet == null ||
                    firstToSet != null && firstToSet.equals(toSet)) {
                firstSet.add(state);
            } else {
                secondSet.add(state);
            }
            }

    }
        if(!secondSet.isEmpty()) {
            splitted = true;
            break;
        }
    }
        if(splitted) {
            for( state in firstSet as Set<Estado>) {
                stateSetMapping.put(state, firstSet);
            }
            for( state in secondSet as Set<Estado>) {
                stateSetMapping.put(state, secondSet);
            }
            sets.add(firstSet as MutableSet<Estado>)
            sets.add(secondSet as MutableSet<Estado>)
        } else {
            sets.add(set);
        }
    }
    fun  createMinimizedAutomaton( automaton:DFA,  stateSetMapping:MutableMap<Estado,Set<Estado>> ,sets:MutableSet<MutableSet<Estado>>):DFA {
        var minimizedAutomaton :DFA=DFA(mutableListOf(), mutableListOf(),Estado("",false), mutableListOf());
        minimizedAutomaton.alfabeto=automaton.alfabeto
        var minimizedSetStateMapping :MutableMap<Set<Estado>, Estado> = HashMap<Set<Estado>, Estado>();
        for(set in sets) {
            var minimizedState:Estado =  Estado("",false);
            minimizedSetStateMapping.put(set, minimizedState);
        }
        var minimizedStates :LinkedList<Estado> =  LinkedList<Estado>();
        for(set in sets) {
        if(set.isEmpty()) {
            continue;
        }
        var state:Estado = set.iterator().next();
        var minimizedState = minimizedSetStateMapping.get(set);
        for( c in automaton.alfabeto) {
            var toState:Estado = automaton.SearchDestiny(state, c)as Estado;
            if(toState != null) {
                var toStateSet = stateSetMapping.get(toState);
                var minimizedToState = minimizedSetStateMapping.get(toStateSet);
                automaton.insertarTransacion(Transicion(minimizedState as Estado, minimizedToState as Estado, c))
            }
        }
        var initial = false;
        for( s in set) {
        if(automaton.estadoInicial.equals(s)) {
            initial = true;
            break;
        }
    }
        if(initial) {
            minimizedStates.addFirst(minimizedState);
        } else {
            minimizedStates.addLast(minimizedState);
        }
    }
        var first = true;
        for( minimizedState in minimizedStates) {
        if(first) {
            minimizedAutomaton.insertarEstado(minimizedState);
            minimizedAutomaton.estadoInicial=minimizedState
            first = false;
        } else {
            minimizedAutomaton.insertarEstado(minimizedState);
        }
    }
        return minimizedAutomaton;
    }


}