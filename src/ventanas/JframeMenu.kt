﻿package ventanas

import com.mxgraph.model.mxCell
import com.mxgraph.swing.mxGraphComponent
import com.mxgraph.util.mxEvent
import com.mxgraph.view.mxGraph
import javafx.embed.swing.SwingNode
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import java.awt.Color
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.util.*
import javax.swing.*
import DFA
import NFA
import NFAE
import Estado
import Transicion
import Automatas
import com.mxgraph.util.mxEventObject

public class JframeMenu : javax.swing.JFrame() {
    /**
     *
     * Creates new form JFrameMenu
     */
    var frame =CrearEstadoJFrame()
    val graph = mxGraph()
    var graphComponent: mxGraphComponent =  mxGraphComponent(graph);
    init {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
            // <editor-fold defaultstate="collapsed" desc="Generated Code">
    fun initComponents() {

        var name: String= " "
        jLabel1 = javax.swing.JLabel();
        jLabel2 = javax.swing.JLabel();
        TipoAutomataCombox = javax.swing.JComboBox<String>();
        CadenaTXTField = javax.swing.JTextField();
        AlfabetoTXTField = javax.swing.JTextField();
        EvaluarBtn = javax.swing.JButton();
        MinimizarBtn = javax.swing.JButton();
        AceptacionLabel = javax.swing.JLabel();
        ConvertirDFA = javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        (jLabel1 as JLabel).setText("Alfabeto ejemplo: 1,0 ");

        (jLabel2 as JLabel).setText("Cadena");
        var vector: Vector<String> = Vector<String>()
        vector.add("DFA")
        vector.add("NFA")
        vector.add("NFAE")

        (TipoAutomataCombox as JComboBox<String>).setModel(javax.swing.DefaultComboBoxModel<String>((vector)))

        (TipoAutomataCombox as JComboBox<String>).addActionListener(java.awt.event.ActionListener() {
            fun actionPerformed(evt:java.awt.event.ActionEvent ) {
                TipoAutomataComboxActionPerformed(evt);
            }
        });

        (CadenaTXTField as JTextField).addActionListener(java.awt.event.ActionListener() {
            fun actionPerformed(evt: java.awt.event.ActionEvent) {
                CadenaTXTFieldActionPerformed(evt);
            }
        });

        (AlfabetoTXTField as JTextField).addActionListener(java.awt.event.ActionListener() {
            fun actionPerformed(evt: java.awt.event.ActionEvent) {
                AlfabetTXTFieldActionPerformed(evt);
            }
        });
        (EvaluarBtn as JButton).setText("Evaluar");
        (EvaluarBtn as JButton).addActionListener( {evt:java.awt.event.ActionEvent->
                 EvaluarBtnActionPerformed(evt)
        });

        (MinimizarBtn as JButton).setText("Minimizar");
        (MinimizarBtn as JButton).addActionListener( {evt:java.awt.event.ActionEvent->

                MinimizarBtnActionPerformed(evt);

        });
        (ConvertirDFA as JButton).setText("Convertir a DFA");
        (ConvertirDFA as JButton).addActionListener(  {evt:java.awt.event.ActionEvent->
                ConvertirDFAActionPerformed(evt);
        });
        graph.setAllowLoops(true)
        graph.setDisconnectOnMove(false)
        graph.setConnectableEdges(false)
        graph.setEdgeLabelsMovable(false)
        graphComponent.getViewport().setBackground(Color.WHITE)
        graphComponent.getConnectionHandler().addListener( mxEvent.CONNECT, { sender: Any, evt: mxEventObject ->
            try {
            var edge = evt.getProperty("cell")as mxCell
            var origen = edge.getSource() as (mxCell)
            var destino = edge.getTarget()as (mxCell)
                if ((TipoAutomataCombox as JComboBox<String>).selectedItem.toString()=="DFA"){
                    dfa.agregarAlfabeto(AlfabetoTXTField?.text as String)
                    var v1 = dfa.obtenerEstado(origen.value.toString())
                    var v2 = dfa.obtenerEstado(destino.value.toString())
                    var valorDeTransicion = valorDeTransicion()
                    if (dfa.verificarLetra(valorDeTransicion) == false) {
                        graph.getModel().remove(evt.getProperty("cell"));
                        return@addListener
                    }
                    var transicion: Transicion = Transicion(v1, v2, valorDeTransicion.toString())
                    if (dfa.insertarTransacion(transicion)) {
                        showMessage("se agrego transicion correctamente")
                    }else{
                        showMessage(" no se agrego transicion correctamente")
                        graph.getModel().remove(evt.getProperty("cell"));
                        return@addListener
                    }
                    var name = valorDeTransicion.toString();
                    edge.setValue(name);
                }else if((TipoAutomataCombox as JComboBox<String>).selectedItem.toString()=="NFA"){
                    nfa.agregarAlfabeto(AlfabetoTXTField?.text as String)
                    var v1 = nfa.obtenerEstado(origen.value.toString())
                    var v2 = nfa.obtenerEstado(destino.value.toString())
                    var valorDeTransicion = valorDeTransicion()
                    if (nfa.verificarLetra(valorDeTransicion) == false) {
                        graph.getModel().remove(evt.getProperty("cell"));
                        return@addListener
                    }
                    var transicion: Transicion = Transicion(v1, v2, valorDeTransicion.toString())
                    if (nfa.insertarTransacion(transicion)) {
                        showMessage("se agrego transicion correctamente")
                    }else{
                        showMessage(" ya existe la transicion")
                        graph.getModel().remove(evt.getProperty("cell"));
                        return@addListener
                    }
                    var name = valorDeTransicion.toString();
                    edge.setValue(name);

                }else if((TipoAutomataCombox as JComboBox<String>).selectedItem.toString()=="NFAE"){
                    nfae.agregarAlfabeto(AlfabetoTXTField?.text as String)
                    var v1 = nfae.obtenerEstado(origen.value.toString())
                    var v2 = nfae.obtenerEstado(destino.value.toString())
                    var valorDeTransicion = valorDeTransicion()
                    if (valorDeTransicion==" "){
                        valorDeTransicion = "ε"
                    }
                    if (nfae.verificarLetra(valorDeTransicion) == false) {
                        graph.getModel().remove(evt.getProperty("cell"));
                        return@addListener
                    }
                    var transicion: Transicion = Transicion(v1, v2, valorDeTransicion)
                    if (nfae.insertarTransacion(transicion)) {
                        showMessage("se agrego transicion correctamente")
                    }else{
                        showMessage("ya existe la transicion ")
                        graph.getModel().remove(evt.getProperty("cell"));
                        return@addListener
                    }
                    var name = valorDeTransicion.toString();
                    edge.setValue(name);
                }

        }catch(e:TypeCastException){
                 graph.getModel().remove(evt.getProperty("cell"));
                return@addListener
        }
        })
        graphComponent.getGraphControl().addMouseListener(object: MouseAdapter(){

            override fun  mouseReleased(e:MouseEvent ) {
                if (e.isPopupTrigger()) {
                    frame.isVisible = true
                    (TipoAutomataCombox as JComboBox<String>).disable()
                    if ((TipoAutomataCombox as JComboBox<String>).selectedItem.toString() == "DFA") {
                        if(dfa.estadoInicial.NombreEstado!=""){
                            frame.EstadoInicialRadioBtn?.setVisible(false)
                        }

                    } else if ((TipoAutomataCombox as JComboBox<String>).selectedItem.toString() == "NFA") {
                        if(nfa.estadoInicial.NombreEstado!=""){
                            frame.EstadoInicialRadioBtn?.setVisible(false)
                        }
                    } else if ((TipoAutomataCombox as JComboBox<String>).selectedItem.toString() == "NFAE") {
                        if(nfae.estadoInicial.NombreEstado!=""){
                            frame.EstadoInicialRadioBtn?.setVisible(false)
                        }
                    }
                }
            }
            override fun mousePressed(e:MouseEvent ) {
                // TODO Auto-generated method stub
                var Exito :Boolean = false
                var aceptable = frame.AceptacionRadioButton?.isSelected
                var inicial = frame.EstadoInicialRadioBtn?.isSelected
                name = frame.NombreEstadoTXTField?.text as String
                frame.isVisible = false
                if(e.getButton() == MouseEvent.BUTTON1) {
                        if(name != ""&& !name.isEmpty()) {
                            var EstadoAinsertar : Estado = Estado(name, aceptable as Boolean)
                            if ((TipoAutomataCombox as JComboBox<String>).selectedItem.toString()=="DFA"){
                                Exito =  dfa.insertarEstado(estado = EstadoAinsertar)
                                if(inicial==true&&Exito==true){
                                   dfa.estadoInicial = EstadoAinsertar
                                }
				dfa.obtenerEstado(EstadoAinsertar.NombreEstado).Vertex= Dibujar(Exito, inicial as Boolean, aceptable,name, e.x, e.y,graph)
                            }else if((TipoAutomataCombox as JComboBox<String>).selectedItem.toString()=="NFA"){
                                Exito = nfa.insertarEstado(EstadoAinsertar)
                                if(inicial==true&&Exito==true){
                                    nfa.estadoInicial = EstadoAinsertar

                                }
				nfa.obtenerEstado(EstadoAinsertar.NombreEstado).Vertex= Dibujar(Exito, inicial as Boolean, aceptable,name, e.x, e.y,graph)
                            }else if((TipoAutomataCombox as JComboBox<String>).selectedItem.toString()=="NFAE"){
                                Exito = nfae.insertarEstado(EstadoAinsertar)
                                if(inicial==true&&Exito==true){
                                    nfae.estadoInicial = EstadoAinsertar
				 }
				nfae.obtenerEstado(EstadoAinsertar.NombreEstado).Vertex= Dibujar(Exito, inicial as Boolean, aceptable,name, e.x, e.y,graph)
                               
                            }
                            name = ""
                            frame.NombreEstadoTXTField?.text = ""
                            frame.EstadoInicialRadioBtn?.isSelected = false
                            frame.AceptacionRadioButton?.isSelected = false
                            Exito = false
                        }
                }
            }
        });
        var layout: javax.swing.GroupLayout = javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(AlfabetoTXTField, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE.toInt())
                                                .addComponent(TipoAutomataCombox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(graphComponent, 600, javax.swing.GroupLayout.DEFAULT_SIZE, 0)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(CadenaTXTField, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                 .addGap(18, 18, 18)
                                                .addComponent(EvaluarBtn)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(AceptacionLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 203, Short.MAX_VALUE.toInt())
                                                .addComponent(ConvertirDFA)
                                                .addGap(18, 18, 18)
                                                .addComponent(MinimizarBtn)))
                                                .addGap(26, 26, 26))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(TipoAutomataCombox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(AlfabetoTXTField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(graphComponent, 600, javax.swing.GroupLayout.DEFAULT_SIZE, 500)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 456, Short.MAX_VALUE.toInt())
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(CadenaTXTField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(EvaluarBtn)
                                        .addComponent(MinimizarBtn)
                                        .addComponent(AceptacionLabel)
                                        .addComponent(ConvertirDFA))

                                .addGap(22, 22, 22))
        );


        pack();
    }// </editor-fold>

    fun CadenaTXTFieldActionPerformed(evt: java.awt.event.ActionEvent) {
        // TODO add your handling code here:
    }

    fun AlfabetTXTFieldActionPerformed(evt: java.awt.event.ActionEvent) {
        // TODO add your handling code here:
    }

    fun ConvertirDFAActionPerformed(evt: java.awt.event.ActionEvent) {
        // TODO add your handling code here:
        if ((TipoAutomataCombox as JComboBox<String>).selectedItem.toString() == "DFA") {
        showMessage("El automata es un DFA")
            return
        }
        graph.model.beginUpdate()
        graph.removeCells(graph.getChildVertices(graph.defaultParent))
        graph.model.endUpdate()
        if ((TipoAutomataCombox as JComboBox<String>).selectedItem.toString() == "NFA") {
           dfa = nfa.enDfa()
            for (estados in dfa.estados) {
               var inicial = false
                if (estados.NombreEstado.equals(dfa.estadoInicial.NombreEstado)){
                   inicial = true
               }
                var x =Random()
                var y = Random()
               estados.Vertex = Dibujar(true,inicial,estados.EsAcceptable,estados.NombreEstado, x.nextInt(500),y.nextInt(500),graph)

            }
            for (estados in dfa.transiciones){
            graph.model.beginUpdate()
                 graph.insertEdge(parent, null, estados.Simbolo, dfa.obtenerEstado(estados.EstadoInicial.NombreEstado).Vertex, dfa.obtenerEstado(estados.EstadoFinal.NombreEstado).Vertex)
             graph.model.endUpdate()

            }
        }else if ((TipoAutomataCombox as JComboBox<String>).selectedItem.toString() == "NFAE") {
	    dfa = nfae.enDfa()
            for (estados in dfa.estados) {
               var inicial = false
                if (estados.NombreEstado.equals(dfa.estadoInicial.NombreEstado)){
                   inicial = true
               }
                var x =Random()
                var y = Random()
               estados.Vertex = Dibujar(true,inicial,estados.EsAcceptable,estados.NombreEstado, x.nextInt(500),y.nextInt(500),graph)

            }
            for (estados in dfa.transiciones){
            graph.model.beginUpdate()
                graph.insertEdge(parent, null,estados.Simbolo,dfa.obtenerEstado(estados.EstadoInicial.NombreEstado).Vertex,dfa.obtenerEstado(estados.EstadoFinal.NombreEstado).Vertex)
            graph.model.endUpdate()

            }
        }

    }

    fun MinimizarBtnActionPerformed(evt: java.awt.event.ActionEvent) {
        // TODO add your handling code here:
    }
    fun TipoAutomataComboxActionPerformed(evt:java.awt.event.ActionEvent)
    {
        // TODO add your handling code here:
    }
    fun EvaluarBtnActionPerformed( evt:java.awt.event.ActionEvent) {
        // TODO add your handling code here:

        var cadena: String = CadenaTXTField?.text as String
        var resultado: String = ""
        var cadenaPermitida: Boolean = false
        if ((TipoAutomataCombox as JComboBox<String>).selectedItem.toString() == "DFA") {
            resultado = dfa.evaluarCadena(cadena).toString()
            cadenaPermitida = dfa.verificarCadena(cadena)
        } else if ((TipoAutomataCombox as JComboBox<String>).selectedItem.toString() == "NFA") {
            resultado = nfa.evaluarCadena(cadena).toString()
            cadenaPermitida = nfa.verificarCadena(cadena)
        } else if ((TipoAutomataCombox as JComboBox<String>).selectedItem.toString() == "NFAE") {
            resultado = nfae.evaluarCadena(cadena).toString()
            cadenaPermitida = nfae.verificarCadena(cadena)
        }
        if (cadenaPermitida == false) {
            showMessage("La cadena tiene caracteres que no son del afabeto")
        } else {
          AceptacionLabel?.text = resultado.toString()
        }
    }
   fun valorDeTransicion():String{
        var  valor :String= "";
        while(true){
            var form:String = JOptionPane.showInputDialog("Digite nombre de Transicion:");
            var valorChar:CharArray=form.toCharArray();
            if(valorChar.size>1){
                showMessage("No caracter no pertenece a alfabeto");
            }else if(valorChar.size==1){
                valor= valorChar.get(0).toString()
                break;
            }
        }
        return valor
    }
    fun  showMessage( msg:String){
        JOptionPane.showMessageDialog(getContentPane(), msg, "Warning",
                JOptionPane.WARNING_MESSAGE);
    }
    private fun mxGraph.update(block: () -> Unit) {
        model.beginUpdate()
        try {
            block()
        }
        finally {
            model.endUpdate()
        }
    }
    private fun Dibujar(Exito: Boolean, inicial: Boolean, aceptable: Boolean,name:String, x: Int, y: Int, graph: mxGraph) :Any{
        var v1:Any =Any()
        var color = "lightblue"
        var forma = "ellipse"
        if(Exito==true) {
            if (inicial == true) {
                color = "lightgreen"
            }

            if (aceptable == true) {

                forma = "doubleEllipse"
            }
            graph.getModel().beginUpdate();
            try {
                var parent = graph.getDefaultParent();
                v1 = graph.insertVertex(parent, null, name, x.toDouble(), y.toDouble(), 50.toDouble(), 50.toDouble(),
                        "resizable=0;editable=0;shape=" + forma + ";whiteSpace=wrap;fillColor=" + color);
            } finally {
                graph.getModel().endUpdate();
            }
        }else{
            showMessage("No se agrego Estado")
        }
	return v1
    }

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify
    var AceptacionLabel: JLabel? = null
    var AlfabetoTXTField: JTextField? = null
    var CadenaTXTField : JTextField? = null
    var EvaluarBtn: JButton? = null
    var MinimizarBtn: JButton? = null
    var TipoAutomataCombox: JComboBox<String>? = null
    var jLabel1: JLabel? = null
    var  jLabel2: JLabel? = null
    var ConvertirDFA: JButton? = null
    var dfa: DFA =DFA( mutableListOf() , mutableListOf() , Estado("",false) ,mutableListOf())
    var nfa: NFA = NFA( mutableListOf() , mutableListOf() , Estado("",false) ,mutableListOf())
    var nfae: NFAE = NFAE( mutableListOf() , mutableListOf() , Estado("",false) ,mutableListOf())


       // End of variables declaration
}

