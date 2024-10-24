package builder;

import utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShapeEditorFrame extends JFrame {
    private final MyEditor editor;
    private JButton lastPressedButton;

    public ShapeEditorFrame() {
        editor = new MyEditor();
        setTitle("Редактор фігур");
        setSize(800, 600);
        setJMenuBar(createMenuBar());
        add(editor);
        initMouseListeners();
    }

    private void initMouseListeners() {
        editor.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                editor.onLBdown((Graphics2D) editor.getGraphics(), e.getX(), e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                editor.onLBup((Graphics2D) editor.getGraphics());
                editor.repaint();
            }
        });

        editor.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                editor.onMouseMove((Graphics2D) editor.getGraphics(), e.getX(), e.getY());
                editor.repaint();
            }
        });
    }

    private JMenuBar createMenuBar() {
        String ellipse = "Еліпс";
        String point = "Точка";
        String rect = "Прямокутник";
        String line = "Лінія";
        String lineOO = "Лінія з еліпсами";
        String cube = "Куб";

        JMenuBar menuBar = new JMenuBar();
        JMenu shapeMenu = new JMenu("Об'єкти");
        JMenu menu1 = new JMenu("Файл");
        JMenu menu2 = new JMenu("Довідка");
        JToolBar toolBar = new JToolBar();
        JPanel panel = new JPanel();

        addToolBarButton(panel, "pic/ellipse.png", e -> {
            editor.start(new EllipseEditor());
            setTitle(ellipse);

            changeButtonColor(e);
        }, ellipse);

        addToolBarButton(panel, "pic/rect.png", e -> {
            editor.start(new RectEditor());
            setTitle(rect);

            changeButtonColor(e);
        }, rect);

        addToolBarButton(panel, "pic/line.png", e -> {
            editor.start(new LineEditor());
            setTitle(line);

            changeButtonColor(e);
        }, line);

        addToolBarButton(panel, "pic/point.png", e -> {
            editor.start(new PointEditor());
            setTitle(point);

            changeButtonColor(e);
        }, point);

        addToolBarButton(panel, "pic/lineOO.png", e -> {
            editor.start(new LineOOEditor());
            setTitle(lineOO);

            changeButtonColor(e);
        }, line);

        addToolBarButton(panel, "pic/cube.png", e -> {
            editor.start(new CubeEditor());
            setTitle(cube);

            changeButtonColor(e);
        }, cube);

        addMenuItem(shapeMenu, point, e -> {
            editor.start(new PointEditor());
            setTitle(point);
        });

        addMenuItem(shapeMenu, line, e -> {
            editor.start(new LineEditor());
            setTitle(line);
        });

        addMenuItem(shapeMenu, rect, e -> {
            editor.start(new RectEditor());
            setTitle(rect);
        });

        addMenuItem(shapeMenu, ellipse, e -> {
            editor.start(new EllipseEditor());
            setTitle(ellipse);
        });

        addMenuItem(shapeMenu, lineOO, e -> {
            editor.start(new LineOOEditor());
            setTitle(lineOO);
        });

        addMenuItem(shapeMenu, cube, e -> {
            editor.start(new CubeEditor());
            setTitle(cube);
        });

        toolBar.add(panel);
        menuBar.add(menu1);
        menuBar.add(shapeMenu);
        menuBar.add(menu2);
        menuBar.add(toolBar);

        return menuBar;
    }

    private void addToolBarButton(JPanel panel, String iconPath, ActionListener action, String toolTipText) {
        JButton button = createButtonWithIcon(iconPath);
        button.setBackground(Color.WHITE);
        button.setToolTipText(toolTipText);
        button.addActionListener(action);
        panel.add(button);
    }

    private void addMenuItem(JMenu menu, String name, ActionListener action) {
        JMenuItem item = new JMenuItem(name);
        item.addActionListener(action);
        menu.add(item);
    }

    private JButton createButtonWithIcon(String iconPath) {
        java.net.URL imgURL = ShapeEditorFrame.class.getResource(iconPath);
        assert imgURL != null;
        ImageIcon icon = new ImageIcon(imgURL);
        Image scaledImage = icon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        return new JButton(new ImageIcon(scaledImage));
    }

    private void changeButtonColor(ActionEvent e) {
        JButton sourceButton = (JButton) e.getSource();

        if (lastPressedButton != null && lastPressedButton != sourceButton) {
            lastPressedButton.setBackground(Color.WHITE);
        }

        sourceButton.setBackground(Color.PINK);

        lastPressedButton = sourceButton;
    }

}
