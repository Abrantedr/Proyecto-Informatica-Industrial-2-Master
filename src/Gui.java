import io.loli.datepicker.DatePicker;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.TimeSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;


public class Gui implements ActionListener {
    private final Api api;
    private TimeSeriesCollection data;
    private String fromDate, toDate;
    private JFrame frame;
    private ChartPanel chart;
    private JPanel buttonPanel, datePickerPanel, chartPanel;
    private JToggleButton demButton, dieButton, gasButton, eolButton, ccButton,
            vapButton, fotButton, hidButton;


    public Gui() {
        // Create API interface object
        api = new Api();

        // Create GUI components
        createButtonPanel();
        createDatePicker();

        // First API call
        makeApiCall();

        // Add chart
        createChart();

        // Create main panel and add components
        createMainPanel();
    }

    public static void main(String[] args) {
        Gui frontend = new Gui();
    }

    public void createMainPanel() {
        // Create main frame
        frame = new JFrame("Red Eléctrica España");

        // Create a grid layout for the main frame
        GridBagLayout grid = new GridBagLayout();
        frame.setLayout(grid);

        // Create a GridBagConstraints object to specify the constraints
        // for each panel
        GridBagConstraints constraints = new GridBagConstraints();

        // Add the top panel to the JFrame with the specified constraints
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.weightx = 1;
        constraints.weighty = 0.5;
        constraints.fill = GridBagConstraints.BOTH;
        frame.add(chartPanel, constraints);

        // Add the first (left) bottom panel to the JFrame with the
        // specified constraints
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.weighty = 0.25;
        frame.add(buttonPanel, constraints);

        // Add the second (right) bottom panel to the JFrame with the
        // specified constraints
        constraints.gridx = 1;
        frame.add(datePickerPanel, constraints);

        // Set frame options
        frame.setLocation(200, 200);
        frame.setVisible(true);
        frame.setSize(860, 750);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void createButtonPanel() {
        // Create a grid layout for the toggle buttons
        GridLayout grid = new GridLayout(4, 2, 5, 7);

        // Create button panel
        buttonPanel = new JPanel(grid);

        // Create toggle buttons
        demButton = new JToggleButton("Demanda total", true);
        dieButton = new JToggleButton("Generación con motores diésel");
        gasButton = new JToggleButton("Generación con turbinas de gas");
        eolButton = new JToggleButton("Generación eólica");
        ccButton = new JToggleButton("Generación en ciclos combinados");
        vapButton = new JToggleButton("Generación en ciclo de vapor");
        fotButton = new JToggleButton("Generación fotovoltaica");
        hidButton = new JToggleButton("Generación hidráulica");

        // Add buttons to button panel
        buttonPanel.add(demButton);
        buttonPanel.add(dieButton);
        buttonPanel.add(gasButton);
        buttonPanel.add(eolButton);
        buttonPanel.add(ccButton);
        buttonPanel.add(vapButton);
        buttonPanel.add(fotButton);
        buttonPanel.add(hidButton);

        // Set a border for button panel
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                BorderFactory.createEmptyBorder(10, 15, 5, 15)));

        // Set action listeners for each button
        demButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent demButton) {
                try {
                    JToggleButton demButtonState =
                            (JToggleButton) demButton.getSource();
                    if (demButtonState.isSelected()) {
                        data.addSeries(Api.demSeries);
                    } else {
                        data.removeSeries(Api.demSeries);
                    }
                } catch (NumberFormatException ignored) {
                    // Catch exception
                }
            }
        });

        dieButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent dieButton) {
                try {
                    JToggleButton dieButtonState =
                            (JToggleButton) dieButton.getSource();
                    if (dieButtonState.isSelected()) {
                        data.addSeries(Api.dieSeries);
                    } else {
                        data.removeSeries(Api.dieSeries);
                    }
                } catch (NumberFormatException ignored) {
                    // Catch exception
                }
            }
        });

        gasButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent gasButton) {
                try {
                    JToggleButton gasButtonState =
                            (JToggleButton) gasButton.getSource();
                    if (gasButtonState.isSelected()) {
                        data.addSeries(Api.gasSeries);
                    } else {
                        data.removeSeries(Api.gasSeries);
                    }
                } catch (NumberFormatException ignored) {
                    // Catch exception
                }
            }
        });

        eolButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent eolButton) {
                try {
                    JToggleButton eolButtonState =
                            (JToggleButton) eolButton.getSource();
                    if (eolButtonState.isSelected()) {
                        data.addSeries(Api.eolSeries);
                    } else {
                        data.removeSeries(Api.eolSeries);
                    }
                } catch (NumberFormatException ignored) {
                    // Catch exception
                }
            }
        });

        ccButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ccButton) {
                try {
                    JToggleButton ccButtonState =
                            (JToggleButton) ccButton.getSource();
                    if (ccButtonState.isSelected()) {
                        data.addSeries(Api.ccSeries);
                    } else {
                        data.removeSeries(Api.ccSeries);
                    }
                } catch (NumberFormatException ignored) {
                    // Catch exception
                }
            }
        });

        vapButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent vapButton) {
                try {
                    JToggleButton vapButtonState =
                            (JToggleButton) vapButton.getSource();
                    if (vapButtonState.isSelected()) {
                        data.addSeries(Api.vapSeries);
                    } else {
                        data.removeSeries(Api.vapSeries);
                    }
                } catch (NumberFormatException ignored) {
                    // Catch exception
                }
            }
        });

        fotButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent fotButton) {
                try {
                    JToggleButton fotButtonState =
                            (JToggleButton) fotButton.getSource();
                    if (fotButtonState.isSelected()) {
                        data.addSeries(Api.fotSeries);
                    } else {
                        data.removeSeries(Api.fotSeries);
                    }
                } catch (NumberFormatException ignored) {
                    // Catch exception
                }
            }
        });

        hidButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent hidButton) {
                try {
                    JToggleButton hidButtonState =
                            (JToggleButton) hidButton.getSource();
                    if (hidButtonState.isSelected()) {
                        data.addSeries(Api.hidSeries);
                    } else {
                        data.removeSeries(Api.hidSeries);
                    }
                } catch (NumberFormatException ignored) {
                    // Catch exception
                }
            }
        });

        // Show button panel
        buttonPanel.setVisible(true);
    }

    public void createDatePicker() {
        // Create a grid layout for the date picker
        GridLayout grid = new GridLayout(3, 2, 5, 5);

        // Create date picker panel
        datePickerPanel = new JPanel(grid);

        // Create select button
        JButton selectPushButton = new JButton("Seleccionar");
        JButton downloadPushButton = new JButton("Descargar");

        // Create date fields with today's date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        fromDate = dateFormat.format(calendar.getTime());
        toDate = fromDate;

        // Create date pickers
        JTextField fromTextField = new JTextField(fromDate);
        JTextField toTextField = new JTextField(fromDate);
        DatePicker fromDatePicker = new DatePicker(fromTextField);
        DatePicker toDatePicker = new DatePicker(toTextField);

        // Create labels
        JLabel fromLabel = new JLabel("Desde: ");
        JLabel toLabel = new JLabel("Hasta: ");

        // Add date pickers and fields to date picker panel
        datePickerPanel.add(fromLabel);
        datePickerPanel.add(fromTextField);
        datePickerPanel.add(toLabel);
        datePickerPanel.add(toTextField);
        datePickerPanel.add(selectPushButton);
        datePickerPanel.add(downloadPushButton);

        // Set picker panel border
        datePickerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                BorderFactory.createEmptyBorder(10, 15, 5, 15)));

        // Show date picker panel
        datePickerPanel.setVisible(true);

        // Define select button behavior
        selectPushButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent selectPushButton) {
                // Clear all time series
                Api.demSeries.clear();
                Api.dieSeries.clear();
                Api.gasSeries.clear();
                Api.eolSeries.clear();
                Api.ccSeries.clear();
                Api.vapSeries.clear();
                Api.fotSeries.clear();
                Api.hidSeries.clear();

                try {
                    // Get date picker field text
                    fromDate = fromTextField.getText();
                    toDate = toTextField.getText();

                    // Make the API call with these two dates
                    makeApiCall();

                    // Change title accordingly
                    if (fromDate.equals(toDate)) {
                        chart.getChart().setTitle("Generación en Canarias (" +
                                fromDate + ")");
                    } else {
                        chart.getChart().setTitle("Generación en Canarias (" +
                                fromDate + " hasta " + toDate + ")");
                    }
                } catch (NumberFormatException ignored) {
                    // Catch exception
                }
            }
        });

        // Define download button behavior
        downloadPushButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent downloadPushButton) {
                try {
                    ChartUtilities.saveChartAsPNG(new File("download.png"),
                            chart.getChart(), 1024, 768);
                } catch (Exception ignored) {
                    // Catch exception
                }
            }
        });
    }

    public void makeApiCall() {
        // Get "from" and "to" dates in Date format
        LocalDate startDate = LocalDate.parse(fromDate);
        LocalDate endDate = LocalDate.parse(toDate);

        // iterate over start date to end date
        LocalDate currentDate = startDate;

        while (currentDate.isBefore(endDate) || currentDate.isEqual(endDate)) {
            // Reconstruct URL and make an API call

            try {
                final String url = Api.getUrl(currentDate.toString());
                Api.download(url, Api.path);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Parse downloaded .json and update TimeSeries objects
            try {
                Api.listContent(Api.path);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Add a day to the current date
            currentDate = currentDate.plusDays(1);
        }
    }

    public void createChart() {
        // Create a flow layout for chart panel
        GridBagLayout gridBagLayout = new GridBagLayout();

        // Create a white chart panel
        chartPanel = new JPanel(gridBagLayout);
        chartPanel.setBackground(Color.WHITE);

        // Set panel border
        chartPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                BorderFactory.createEmptyBorder(10, 15, 5, 15)));

        // Add default graphic
        try {
            data = new TimeSeriesCollection();
            data.addSeries(Api.demSeries);

            // Change title accordingly
            String title = null;
            if (fromDate.equals(toDate)) {
                title = "Generación en Canarias (" + fromDate + ")";
            } else {
                title = "Generación en Canarias (" + fromDate + " hasta " +
                        toDate + ")";
            }
            // Create graphic
            JFreeChart graphic = ChartFactory.createTimeSeriesChart(
                    title,
                    "Fecha y hora",     // x-axis
                    "Demanda (MW)",     // y-axis
                    data,               // time series
                    true,               // show legend
                    true,               // use tooltip
                    false               // generate URLs
            );

            // Change color
            graphic.setBackgroundPaint(Color.WHITE);

            // Add chart to chart panel
            chart = new ChartPanel(graphic);

            GridBagConstraints constraints = new GridBagConstraints();
            // Add the top panel to the JFrame with the specified constraints
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.gridwidth = 2;
            constraints.weightx = 1;
            constraints.weighty = 0.5;
            constraints.fill = GridBagConstraints.BOTH;
            chartPanel.add(chart, constraints);

            // Show chart panel
            chartPanel.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
