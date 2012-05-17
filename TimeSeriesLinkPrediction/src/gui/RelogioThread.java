package gui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import log.States;

public class RelogioThread extends JFrame{

	JLabel label; // label onde será impressa a hora atual
	States states;

	public RelogioThread(){//construtor
		super("RELÓGIO");//seta o título do JFrame
		setSize(250,60);//seta o tamanho do JFrame
		setVisible(true);//seta visível
		Container tela = getContentPane();//container onde ficara o label
		label = new JLabel("Inicio");//instancia o label
		tela.add(label);//adiciona o label à tela
		tela.setLayout(null);
		label.setBounds(10,0,100,85);//seta a posição do label
		states = new States();
		JButton button = new JButton("init");
		button.setBounds(150,0,100,30);
		iniciaRelogio();//inicia o relógio
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				incrI();				
			}
		});
		tela.add(button);
		
	}

	public void incrI(){
		try {
			states.setMainState("Texto 1");
			Thread.sleep(1000);
			states.setMainState("Texto 2");
			Thread.sleep(1000);
			states.setMainState("Texto 3");
			Thread.sleep(1000);
			states.setMainState("Texto 4");
			Thread.sleep(1000);
			states.setMainState("Texto 5");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void iniciaRelogio(){
		new Thread(){
			@Override
			public void run() {
				while(true){
					label.setText(states.getMainState());
					try {
						sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();//inicia a thread.
	}

	public static void main(String[] args) {
		new RelogioThread();
	}

}