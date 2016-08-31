import automatas.PDA
import com.mxgraph.model.mxCell
import com.mxgraph.swing.mxGraphComponent
import com.mxgraph.util.mxEvent
import com.mxgraph.util.mxEventObject
import com.mxgraph.view.mxGraph
import java.awt.Color
import java.awt.event.ActionEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*
import com.mxgraph.util.*
import java.util.*


/**
 * Created by user02 on 30/08/2016.
 */
class testPDA():JFrame(){
    var jButtonProbarAutomata: JButton? = null
    var jLabel1: JLabel? = null
    var jLabel2: JLabel? = null
    var jLabel3: JLabel? = null
    var jLabel4: JLabel? = null
    var jLabel5: JLabel? = null
    var jLabel6: JLabel? = null
    var jMenuFile: javax.swing.JMenu? = null
    var jMenuItemClose: javax.swing.JMenuItem? = null
    var jMenuItemNewState: javax.swing.JMenuItem? = null
    var jMenuItemNewTransition: javax.swing.JMenuItem? = null
    var jMenuItemDeleteState: javax.swing.JMenuItem? = null
    var jMenuItemCreateInitialState: javax.swing.JMenuItem? = null
    var jMenuItemCreateAcceptanceState: javax.swing.JMenuItem? = null
    var jMenuItemChangeInitialState: javax.swing.JMenuItem? = null
    var jMenuItemDeleteTransition: javax.swing.JMenuItem? = null
    var jSeparator3: javax.swing.JPopupMenu.Separator? = null
    var jMenuItemOpen: javax.swing.JMenuItem? = null
    var jMenuItemSaveAs: javax.swing.JMenuItem? = null
    var jMenuItemAddEstado: javax.swing.JMenuItem? = null
    var jMenuItemCrearEstadoInicial: javax.swing.JMenuItem? = null
    var jMenuItemCrearEstadoAceptacion: javax.swing.JMenuItem? = null
    var jMenuItemCambiarEstadoInicial: javax.swing.JMenuItem? = null
    var jMenuItemAddTransition: javax.swing.JMenuItem? = null
    var jMenuItemDeleteEstado: javax.swing.JMenuItem? = null
    var jMenuItemDeleteTransicion: javax.swing.JMenuItem? = null
    var jMenuOptions: javax.swing.JMenu? = null
    var jSeparator1: javax.swing.JPopupMenu.Separator? = null
    var jTextFieldAlfabeto: javax.swing.JTextField? = null
    var jTextFieldCadena: javax.swing.JTextField? = null
    var jTextFieldSimboloDePila: javax.swing.JTextField? = null
    var jTextFieldSimboloEspecial: javax.swing.JTextField? = null
    var jPopupMenuForm: javax.swing.JPopupMenu? = null
    var jSeparator2: javax.swing.JPopupMenu.Separator? = null
    var jSeparator4: javax.swing.JPopupMenu.Separator? = null
    var jSeparator5: javax.swing.JPopupMenu.Separator? = null
    var jSeparator6: javax.swing.JPopupMenu.Separator? = null
    var jSeparator7: javax.swing.JPopupMenu.Separator? = null
    var jSeparator8: javax.swing.JPopupMenu.Separator? = null
    var jSeparator9: javax.swing.JPopupMenu.Separator? = null
    val graph : mxGraph = mxGraph()
    var graphComponent: mxGraphComponent =  mxGraphComponent(graph)
    var valor = 0;
    var pda: PDA



    init {
        initComponents();
        pda = PDA(mutableListOf(), mutableListOf(), Estado("", false), mutableListOf())
    }

    fun initComponents() {

        jLabel1 = javax.swing.JLabel();
        jLabel3 = javax.swing.JLabel();
        jLabel4 = javax.swing.JLabel();
        jLabel5 = javax.swing.JLabel();
        jLabel6 = javax.swing.JLabel();
        jTextFieldAlfabeto = javax.swing.JTextField();
        jTextFieldCadena = javax.swing.JTextField();
        jTextFieldSimboloDePila = javax.swing.JTextField();
        jTextFieldSimboloEspecial = javax.swing.JTextField();
        jLabel2 = javax.swing.JLabel();
        jButtonProbarAutomata = javax.swing.JButton();
        jMenuFile = javax.swing.JMenu();
        jMenuItemOpen = javax.swing.JMenuItem();
        jMenuItemSaveAs = javax.swing.JMenuItem();
        jSeparator1 = javax.swing.JPopupMenu.Separator();
        jMenuItemClose = javax.swing.JMenuItem();
        jMenuOptions = javax.swing.JMenu();
        jMenuItemNewState = javax.swing.JMenuItem();
        jMenuItemNewTransition = javax.swing.JMenuItem();
        jMenuItemDeleteState = javax.swing.JMenuItem();
        jSeparator3 = javax.swing.JPopupMenu.Separator();
        jPopupMenuForm = javax.swing.JPopupMenu();
        jMenuItemAddEstado = javax.swing.JMenuItem();
        jMenuItemAddTransition = javax.swing.JMenuItem();
        jMenuItemDeleteEstado = javax.swing.JMenuItem();
        jSeparator2 = javax.swing.JPopupMenu.Separator();
        jSeparator4 = javax.swing.JPopupMenu.Separator();
        jSeparator5 = javax.swing.JPopupMenu.Separator();
        jSeparator6 = javax.swing.JPopupMenu.Separator();
        jSeparator7 = javax.swing.JPopupMenu.Separator();
        jSeparator8 = javax.swing.JPopupMenu.Separator();
        jSeparator9 = javax.swing.JPopupMenu.Separator();
        jMenuItemCrearEstadoInicial = javax.swing.JMenuItem();
        jMenuItemCrearEstadoAceptacion = javax.swing.JMenuItem();
        jMenuItemCreateInitialState = javax.swing.JMenuItem();
        jMenuItemCreateAcceptanceState = javax.swing.JMenuItem();
        jMenuItemChangeInitialState = javax.swing.JMenuItem();
        jMenuItemCambiarEstadoInicial = javax.swing.JMenuItem();
        jMenuItemDeleteTransicion = javax.swing.JMenuItem();
        jMenuItemDeleteTransition = javax.swing.JMenuItem();


        (jMenuItemAddEstado as JMenuItem).setText("Agregar Estado");
        (jMenuItemAddEstado as JMenuItem).addActionListener({ e: ActionEvent ->
            agregarEstado(e);
        });
        jPopupMenuForm?.add(jMenuItemAddEstado);
        jPopupMenuForm?.add(jSeparator4);

        (jMenuItemAddTransition as JMenuItem).setText("Agregar Transicion");
        (jMenuItemAddTransition as JMenuItem).addActionListener({ e: ActionEvent ->
            agregarTrasicion(e);
        });
        jPopupMenuForm?.add(jMenuItemAddTransition);
        jPopupMenuForm?.add(jSeparator2);

        (jMenuItemDeleteEstado as JMenuItem).setText("Eliminar Estado");
        (jMenuItemDeleteEstado as JMenuItem).addActionListener({ e: ActionEvent ->

        });
        jPopupMenuForm?.add(jMenuItemDeleteEstado);
        jPopupMenuForm?.add(jSeparator5);

        (jMenuItemDeleteTransicion as JMenuItem).setText("Eliminar Transicion");
        (jMenuItemDeleteTransicion as JMenuItem).addActionListener({ e: ActionEvent ->

        });
        jPopupMenuForm?.add(jMenuItemDeleteTransicion);
        jPopupMenuForm?.add(jSeparator9);

        (jMenuItemCrearEstadoInicial as JMenuItem).setText("Crear Estado Inicial");
        (jMenuItemCrearEstadoInicial as JMenuItem).addActionListener({ e: ActionEvent ->
            crearEstadoInicial(e);
        });
        jPopupMenuForm?.add(jMenuItemCrearEstadoInicial);
        jPopupMenuForm?.add(jSeparator7);

        (jMenuItemCrearEstadoAceptacion as JMenuItem).setText("Crear Estado de Aceptacion");
        (jMenuItemCrearEstadoAceptacion as JMenuItem).addActionListener({ e: ActionEvent ->

        });
        jPopupMenuForm?.add(jMenuItemCrearEstadoAceptacion);

        (jSeparator8 as JPopupMenu.Separator).setVisible(false);
        jPopupMenuForm?.add(jSeparator8);

        (jMenuItemCambiarEstadoInicial as JMenuItem).setText("Cambiar Estado Inicial");
        (jMenuItemCambiarEstadoInicial as JMenuItem).addActionListener({ e: ActionEvent ->
            cambiarEstadoInicial(e);
        });
        (jMenuItemCambiarEstadoInicial as JMenuItem).setVisible(false);
        jPopupMenuForm?.add(jMenuItemCambiarEstadoInicial);



        graph.setAllowLoops(true);
        graph.setDisconnectOnMove(false);
        graph.setConnectableEdges(false);
        graph.setEdgeLabelsMovable(false);
        graphComponent = mxGraphComponent(graph);
        graphComponent.getViewport().setBackground(Color.WHITE);
        graphComponent.getConnectionHandler().addListener(mxEvent.CONNECT, { sender: Any, evt: mxEventObject ->
            var edge = evt.getProperty("cell") as (mxCell)
            var origen = edge . getSource () as (mxCell)as (mxCell)
            var destino =  edge . getTarget () as (mxCell)
            var v1 = obtenerEstado(origen);
            var v2 = obtenerEstado(destino);
            if (v2 == null) {
                graph.getModel().remove(evt.getProperty("cell"));
                return@addListener
            }
            var nombre = escogerTransicion();
            if (nombre.isEmpty()) {
                graph.getModel().remove(evt.getProperty("cell"));
                return@addListener
            }


            edge.setValue(nombre);
            pda.AddTransition(v1!!, v2, nombre, edge);
        });
        graphComponent.getGraphControl().addMouseListener(object : MouseAdapter() {

            override fun mouseReleased(e: MouseEvent) {
                if (e.isPopupTrigger()) {
                    (jPopupMenuForm as JPopupMenu).show(getContentPane(), e.getX(), e.getY());
                }
            }

            override fun mousePressed(e: MouseEvent) {
                // TODO Auto-generated method stub
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    var x = e.getX();
                    var y = e.getY();
                    graph.getModel().beginUpdate();
                    try {
                        var parent = graph.getDefaultParent();
                        var name = (valor++).toString();
                        var v1 = graph.insertVertex(parent, null, "I" + name, x.toDouble(), y.toDouble(), 50.toDouble(), 50.toDouble(),
                                "resizable=0;editable=0;shape=ellipse;whiteSpace=wrap;"
                                        + "fillColor=lightblue");

                        pda.insertarEstado(Estado("I" + name, false))
                        ;
                    } finally {
                        graph.getModel().endUpdate();
                    }
                }
            }
        });


        (jLabel1 as JLabel).setText("Alfabeto");

        (jLabel2 as JLabel).setText("Cadena");

        (jLabel4 as JLabel).setText("TIP: No olvidar agregar todo el alfabeto. Es posible que no acepte cadena!");
        (jLabel3 as JLabel).setText("PROTIP: Doble-Click para agregar Estado!");
        (jLabel6 as JLabel).setText("TIP: Si desea no agregar nada a la pila o consumir nada de la entrada utilize este simbolo:");

        (jTextFieldSimboloEspecial as JTextField).setEditable(false);
        (jTextFieldSimboloEspecial as JTextField).setText("ε");


        (jLabel5 as JLabel).setText("Simbolo inicial de Pila");

        (jButtonProbarAutomata as JButton).setText("Probar");
        (jButtonProbarAutomata as JButton).addActionListener({ e: ActionEvent ->
        });
        (jButtonProbarAutomata as JButton).setBackground(Color.WHITE);
        (jMenuFile as JMenu).setText("Archivo");

        (jMenuItemOpen as JMenuItem).setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.ALT_MASK));
        (jMenuItemOpen as JMenuItem).setText("Abrir");

        jMenuFile?.add(jMenuItemOpen);

        (jMenuItemSaveAs as JMenuItem).setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        (jMenuItemSaveAs as JMenuItem).setText("Guardar como..");

        jMenuFile?.add(jMenuItemSaveAs);
        jMenuFile?.add(jSeparator1);

        (jMenuItemClose as JMenuItem).setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        (jMenuItemClose as JMenuItem).setText("Cerrar");
        (jMenuItemClose as JMenuItem).addActionListener({ evt: java.awt.event.ActionEvent ->
            cerrarVentana(evt);
        });
        jMenuFile?.add(jMenuItemClose);

        jMenuBar?.add(jMenuFile);

        (jMenuOptions as JMenu).setText("Opciones");

        (jMenuItemNewState as JMenuItem).setText("Agregar Estado");
        (jMenuItemNewState as JMenuItem).addActionListener({ e: ActionEvent ->
            agregarEstado(e);
        });
        jMenuOptions?.add(jMenuItemNewState);

        (jMenuItemNewTransition as JMenuItem).setText("Agregar Transicion");
        (jMenuItemNewTransition as JMenuItem).addActionListener({ e: ActionEvent ->
            agregarTrasicion(e);
        });
        jMenuOptions?.add(jMenuItemNewTransition);
        jMenuOptions?.add(jSeparator3);

        (jMenuItemDeleteState as JMenuItem).setText("Eliminar Estado");
        (jMenuItemDeleteState as JMenuItem).addActionListener({ e: ActionEvent ->

        });
        jMenuOptions?.add(jMenuItemDeleteState);

        (jMenuItemDeleteTransition as JMenuItem).setText("Eliminar Transicion");
        (jMenuItemDeleteTransition as JMenuItem).addActionListener({ e: ActionEvent ->

        });
        jMenuOptions?.add(jMenuItemDeleteTransition);
        jMenuOptions?.add(jSeparator6);

        (jMenuItemCreateInitialState as JMenuItem).setText("Crear Estado Inicial");
        (jMenuItemCreateInitialState as JMenuItem).addActionListener({ e: ActionEvent ->
            crearEstadoInicial(e);
        });
        jMenuOptions?.add(jMenuItemCreateInitialState);

        (jMenuItemCreateAcceptanceState as JMenuItem).setText("Crear Estado de Aceptacion");
        (jMenuItemCreateAcceptanceState as JMenuItem).addActionListener({ e: ActionEvent ->
            });
        jMenuOptions?.add(jMenuItemCreateAcceptanceState);

        (jMenuItemChangeInitialState as JMenuItem).setText("Cambiar Estado Inicial");
        (jMenuItemChangeInitialState as JMenuItem).addActionListener({ e: ActionEvent ->
            cambiarEstadoInicial(e);
        });
        (jMenuItemChangeInitialState as JMenuItem).setVisible(false);
        jMenuOptions?.add(jMenuItemChangeInitialState);

        jMenuBar?.add(jMenuOptions);
        setJMenuBar(jMenuBar);

        var layout = javax.swing.GroupLayout(getContentPane())
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldAlfabeto, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldCadena, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldSimboloDePila, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonProbarAutomata)
                                .addContainerGap(26, Short.MAX_VALUE.toInt())).addComponent(jLabel4).
                        addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldSimboloEspecial, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(graphComponent, 500, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE.toInt())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(jTextFieldAlfabeto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextFieldCadena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2)
                                        .addComponent(jTextFieldSimboloDePila, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5)
                                        .addComponent(jButtonProbarAutomata))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabel4)
                                .addComponent(jLabel6)
                                .addComponent(jTextFieldSimboloEspecial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3)
                                .addComponent(graphComponent, 500, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE.toInt()))
        );


    }
    fun cerrarVentana(evt: java.awt.event.ActionEvent) {
        setVisible(false);
        dispose();
    }

    fun agregarEstado(evt: java.awt.event.ActionEvent) {
        var name = (valor++).toString();
        var add = NewEstadoPDA(name);
    }

    fun crearEstadoInicial(e: ActionEvent) {
        if (prologoEstado())
            return;
        var name = JOptionPane.showInputDialog("Digite nombre de Estado Inicial:");
        if (name == null || name.isEmpty()) {
            showMessage("No se creo Estado Inicial!");
            return;
        }
        var v1 = obtenerEstado(name);
        if (v1 == null) {
            showMessage("No hay estados con ese nombre!");
            return;
        }
        crearEstadoInicial(v1);
    }

    fun crearEstadoInicial(v1: Estado) {
        graph.getModel().setStyle(v1.Vertex, "resizable=0;editable=0;shape=ellipse;whiteSpace=wrap;"
                + "fillColor=lightgreen");
        pda.estadoInicial = v1;
        jMenuItemCrearEstadoInicial?.setVisible(false);
        jMenuItemCreateInitialState?.setVisible(false);
        jSeparator7?.setVisible(false);
        jSeparator8?.setVisible(true);
        jMenuItemCambiarEstadoInicial?.setVisible(true);
        jMenuItemChangeInitialState?.setVisible(true);
    }

    fun showMessage(msg: String) {
        JOptionPane.showMessageDialog(getContentPane(), msg, "Warning",
                JOptionPane.WARNING_MESSAGE);
    }
    fun agregarTrasicion(evt:java.awt.event.ActionEvent ) {
        if (prologoEstado())
            return;
        var name = JOptionPane . showInputDialog ("Digite nombre de Estado partida:");

        if (name == null || name.isEmpty()) {
            showMessage("No se agrego nada!!");
            return;
        }
        var v1 = obtenerEstado (name);
        if (v1 == null) {
            showMessage("No existe etado con ese nombre!");
            return;
        }
        name = JOptionPane.showInputDialog("Digite nombre de Estado destino:");
        if (name == null || name.isEmpty()) {
            showMessage("No se agrego nada!!");
            return;
        }
        var v2 = obtenerEstado (name);
        if (v2 == null) {
            showMessage("No existe etado con ese nombre!");
            return;
        }
        var nombre = escogerTransicion ();
        if (nombre.isEmpty()) {
            return;
        }
        var add = NewTransitionPDA(v1, v2, nombre);
    }
    fun cambiarEstadoInicial(e: ActionEvent) {
        var name = JOptionPane . showInputDialog ("Digite nuevo nombre de Estado Inicial:");
        if (name == null || name.isEmpty()) {
            showMessage("No se cambio Estado Inicial!");
            return;
        }
        var v1 = pda.estadoInicial;
        var v2 = obtenerEstado (name);
        if (v2 == null) {
            showMessage("No hay estados con ese nombre!");
            return;
        }
        if (revisarModeloDeAcuerdoAEstadosFinales(v1, v2)) {
            return;
        }
        cambiarEstadoInicial(v1, v2);
    }
    fun cambiarEstadoInicial(v1:Estado ,v2: Estado ) {
        graph.getModel().setStyle(v1.Vertex, "resizable=0;editable=0;shape=ellipse;whiteSpace=wrap;"
                + "fillColor=lightblue");
        graph.getModel().setStyle(v2.Vertex, "resizable=0;editable=0;shape=ellipse;whiteSpace=wrap;"
                + "fillColor=lightgreen");
        pda.estadoInicial = v2;
    }
    fun  revisarModeloDeAcuerdoAEstadosFinales(v1:Estado ,v2: Estado):Boolean {
        var final1 :Estado? =null
        var final2:Estado? = null;

        for ( estado in pda.estados){
            if (estado.NombreEstado.equals(v1.NombreEstado)&&estado.EsAcceptable) {
                final1 = estado;
            }
            if (estado.NombreEstado.equals(v2.NombreEstado)&&estado.EsAcceptable) {
                final2 = estado;
            }
        }
        if (final1 != null && final2 != null) {
            graph.getModel().setStyle(v1.Vertex, "resizable=0;editable=0;shape=doubleEllipse;whiteSpace=wrap;"
                    + "fillColor=lightblue");
            graph.getModel().setStyle(v2.Vertex, "resizable=0;editable=0;shape=doubleEllipse;whiteSpace=wrap;"
                    + "fillColor=lightgreen");
            pda.estadoInicial = v2;
            return true;
        }
        if (final1 != null && final2 == null) {
            graph.getModel().setStyle(v1.Vertex, "resizable=0;editable=0;shape=doubleEllipse;whiteSpace=wrap;"
                    + "fillColor=lightblue");
            graph.getModel().setStyle(v2.Vertex, "resizable=0;editable=0;shape=ellipse;whiteSpace=wrap;"
                    + "fillColor=lightgreen");
            pda.estadoInicial = v2;
            return true;
        }
        if (final1 == null && final2 != null) {
            graph.getModel().setStyle(v1.Vertex, "resizable=0;editable=0;shape=ellipse;whiteSpace=wrap;"
                    + "fillColor=lightblue");
            graph.getModel().setStyle(v2.Vertex, "resizable=0;editable=0;shape=doubleEllipse;whiteSpace=wrap;"
                    + "fillColor=lightgreen");
            pda.estadoInicial = v2;
            return true;
        }
        return false;
    }


    fun convertirAEstadoFinal(v1:Estado ) {
        graph.getModel().setStyle(v1.Vertex, "resizable=0;editable=0;shape=doubleEllipse;whiteSpace=wrap;"
                + "fillColor=lightblue");
        v1.EsAcceptable = true
    }
    fun siEsEstadoInicial(v1:Estado ):Boolean {
        if (v1.NombreEstado.equals(pda.estadoInicial.NombreEstado)) {
            graph.getModel().setStyle(v1.Vertex, "resizable=0;editable=0;shape=doubleEllipse;whiteSpace=wrap;"
                    + "fillColor=lightgreen");
            pda.estadoInicial.EsAcceptable = true;
            return true;
        }
        return false;
    }
    fun  verificarSiYaEsEstadoAceptacion(name:String ):Boolean  {
        for ( estado in  pda.estados) {
            if (estado.NombreEstado.equals(name)&&estado.EsAcceptable) {
                showMessage("Ya es un estado de Aceptaciion!");
                return true;
            }
        }
        return false;
    }
    fun  prologoEstado():Boolean {
        if (pda.estados.isEmpty()) {
            showMessage("No hay estados!");
            return true;
        }
        return false;
    }
    fun escogerTransicion():String {
        var nombre = ""
        while (true) {
            var name = JOptionPane.showInputDialog ("Digite nombre de Transicion:");
            if (name == null || name.isEmpty()) {
                showMessage("No se ingreso nada!!");
                break;
            }
            var na = name.replace (","," ");
            if (na.length != 3) {
                showMessage("Se debe ingresar caracter a consumir, pila a consumir, pila a agregar\nEjemplo: 0,z0,0z0");
            } else if (na.length == 3) {
                nombre = name;
                break;
            }
        }
        return nombre;
    }
    fun   prologoTransicion():Boolean {
        if (pda.transiciones.isEmpty()) {
            showMessage("No hay transiciones!");
            return true;
        }
        return false;
    }
    fun  obtenerEstado( nombre:String):Estado? {
        for ( estado in pda.estados)
        {
            if (estado.NombreEstado.equals(nombre)) {
                return estado;

            }
        }
        return null;
    }
    fun prologoAntesDeProbar():Boolean {
        if (pda.estados.isEmpty()) {
            showMessage("No hay estado!");
            return true;
        }
        if (pda.estadoInicial.NombreEstado.isEmpty()) {
            showMessage("Debe existir un estado inicial!");
            return true;
        }
        if (jTextFieldAlfabeto?.getText()!!.isEmpty()) {
            showMessage("Porfavor ingrese alfabeto!");
            return true;
        }
        if (jTextFieldSimboloDePila?.getText()!!.isEmpty()) {
            showMessage("Porfavor ingrese nombre de simbolo inicial de pila!");
            return true;
        }
        return false;
    }
    fun seCreaAlfabeto(alfabeto:CharArray):Boolean {
        var i = 0
        while  ( i < alfabeto.size) {
            var j = i+ 1
            while  (  j < alfabeto.size) {
                if (alfabeto[j] == alfabeto[i]) {
                    showMessage("No es necesario agregar dos veces el mismo digito o letra al alfabeto!");
                    return false;
                }
                j++
            }
            i++
        }

        if (!pda.alfabeto.isEmpty()) {
            pda.alfabeto = mutableListOf();
        }
        i = 0;
        while (i < alfabeto.size){
            pda.alfabeto.add(alfabeto[i].toString());
            i++
        }
        return true;
    }
    fun obtenerEstado(vertex: mxCell):Estado?  {
        for (estado in pda.estados)
        {
            if (estado.Vertex.equals(vertex)) {
                return estado;

            }
        }
        return null;
    }

    fun getGraph(): mxGraph {
        return graph
    }
}
