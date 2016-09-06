package com.myschool.web.theme;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.myschool.loaders.db.DatabaseUtil;

public class ThemesGenerator {

    private static final String WHITE = "#EEEEEE";

    private static final String DISABLED = "#AAA";

    private static final String THEMES = "templates/themes/ThemeDefinitions.csv";

    private static final String CSS_TEMPLATE = "templates/themes/CSSTemplate.css";

    public static void main(String[] args) throws IOException {

        if (args.length != 1 || args[0].trim().length() == 0) {
            System.err.println("Usage: com.myschool.web.theme.ThemesGenerator <themes_output_directory>");
            System.exit(1);
        }
        File themesOutput = new File(args[0]);
        if (!themesOutput.exists() || !themesOutput.isDirectory()) {
            System.err.println("Themes output directory does not exist or is not a directory.");
            System.exit(1);
        }
        ThemesGenerator themesGenerator = new ThemesGenerator();
        themesGenerator.generateAllThemes(themesOutput);
        System.exit(0);
    }

    private void generateAllThemes(File themesOutput) {
        Connection connection = null;
        InputStream themeDfnStream = null;
        InputStream cssTemplateStream = null;
        PreparedStatement prepareStatement = null;

        try {
            connection = DatabaseUtil.getConnection();
            System.out.println("Will Generate Themes in " + themesOutput.getAbsolutePath());
            deleteOldThemes(themesOutput);
            clearThemesFromDB(connection);

            prepareStatement = connection.prepareStatement("INSERT INTO USER_THEME VALUES(?, ?) ");

            cssTemplateStream = getClass().getClassLoader().getResourceAsStream(CSS_TEMPLATE);
            String cssTemplate = IOUtils.toString(cssTemplateStream);
            //System.out.println(cssTemplate);

            themeDfnStream = getClass().getClassLoader().getResourceAsStream(THEMES);
            List<String> lines = IOUtils.readLines(themeDfnStream);
            List<Theme> themes = getThemes(lines);
            System.out.println("Found " + themes.size() + " themes to be created");

            for (int index = 0; index<themes.size(); index++) {
                Theme theme = themes.get(index);
                String code = theme.getCode();
                String name = theme.getName();

                Map<String, String> tokensMap = getTokens(theme);
                // Generate CSS File.
                String cssText = generateCssText(cssTemplate, tokensMap);
                File themeFile = new File(themesOutput, code + ".css");
                FileUtils.write(themeFile, cssText);

                prepareStatement.setString(1, code);
                prepareStatement.setString(2, name);
                prepareStatement.addBatch();
                System.out.println("Generated Theme: " + code);
            }
            prepareStatement.executeBatch();
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            exception.printStackTrace();
            System.exit(1);
        } finally {
            try {
                IOUtils.closeQuietly(themeDfnStream);
                IOUtils.closeQuietly(cssTemplateStream);
                DatabaseUtil.close(prepareStatement);
                DatabaseUtil.close(connection);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    private void clearThemesFromDB(Connection connection) throws SQLException {
        PreparedStatement prepareStatement = connection.prepareStatement("DELETE FROM USER_THEME");
        prepareStatement.executeUpdate();
        DatabaseUtil.close(prepareStatement);
    }

    private void deleteOldThemes(File themesOutput) {
        System.out.println(">>> Deleting Old Theme Files");
        File[] oldThemes = themesOutput.listFiles();
        for (File oldTheme : oldThemes) {
            String themeName = oldTheme.getName();
            if (oldTheme.isFile() && oldTheme.getName().endsWith(".css")) {
                boolean delete = oldTheme.delete();
                if (delete) {
                    //System.out.println("Deleted: " + themeName);
                } else {
                    System.out.println("Cannot delete: " + themeName);
                }
            }
        }
    }

    private static List<Theme> getThemes(List<String> lines) throws IOException {
        List<Theme> themes = new ArrayList<Theme>();
        for (String line : lines) {
            String[] values = line.split(",");
            if (values != null && values.length == 7) {
                Theme theme = new Theme();
                theme.setCode(values[0].trim());
                theme.setName(values[1].trim());
                theme.setPrimary(values[2].trim());
                theme.setPrimaryVariant(values[3].trim());
                theme.setSecondary(values[4].trim());
                theme.setSecondaryVariant(values[5].trim());
                theme.setGrayedOut(values[6].trim());
                themes.add(theme);
            }
        }
        themes.remove(0);
        return themes;
    }

    private static String generateCssText(String cssTemplateText, Map<String, String> tokensMap) throws IOException {
        String generated = cssTemplateText.replaceAll("    ", "    ");
        Set<String> keySet = tokensMap.keySet();
        for (String key : keySet) {
            String value = tokensMap.get(key);
            generated = generated.replaceAll(key, value);
        }
        return generated;
    }

    private static Map<String, String> getTokens(Theme theme) {
        Map<String, String> tokens = new HashMap<String, String>();

        //tokens.put("@@Page::Background@@", "#212121");
        tokens.put("@@Page::Background@@", theme.getPrimaryVariant());
        tokens.put("@@Header::Background@@", theme.getPrimary());
        tokens.put("@@Footer::Background@@", theme.getPrimary());
        tokens.put("@@Footer::Color@@", WHITE);

        tokens.put("@@FooterLink::Row:Background@@", theme.getGrayedOut());
        tokens.put("@@FooterLink::Title-Odd:Background@@", theme.getSecondary());
        tokens.put("@@FooterLink::Title-Even:Background@@", theme.getPrimaryVariant());
        tokens.put("@@FooterLink::Link:Color@@", theme.getPrimary());
        tokens.put("@@FooterLink::Link:Seperator@@", theme.getPrimaryVariant());
        

        tokens.put("@@Menu::Background@@", theme.getSecondary());
        //tokens.put("@@Menu::Shade1@@", theme.getSecondary());
        //tokens.put("@@Menu::Shade2@@", theme.getSecondary());
        tokens.put("@@Menu::Border@@", theme.getPrimary());
        tokens.put("@@Menu::Hover@@", theme.getPrimaryVariant());
        tokens.put("@@Menu::List:Background@@", "#333");
        tokens.put("@@Menu::List:LastItem@@", theme.getSecondary());

        tokens.put("@@Heading::Title:Background@@", theme.getPrimary());
        tokens.put("@@Heading::Title:Color@@", "#FFF");

        //tokens.put("@@Accordion::Background@@", theme.getGrayedOut());
        //tokens.put("@@Accordion::Hover@@", theme.getPrimary());
        //tokens.put("@@Accordion::Selected@@", theme.getPrimary());

        tokens.put("@List::Selected:Background@@", theme.getSecondary());
        tokens.put("@List::Selected:Color@@", WHITE);

        //tokens.put("@@Input::Text:Focus@@", theme.getPrimaryVariant());
        tokens.put("@@Input::Button:Disabled.Background@@", DISABLED);
        tokens.put("@@Input::Button:Disabled.Border@@", DISABLED);
        tokens.put("@@Input::Button:Border@@", theme.getSecondaryVariant());
        tokens.put("@@Input::Button:Background@@", theme.getSecondary());
        tokens.put("@@Input::Button:Hover@@", theme.getSecondaryVariant());
        
        tokens.put("@@Link::Color@@", theme.getPrimary());

        tokens.put("@@Input::Calendar:Title@@", theme.getSecondary());
        tokens.put("@@Input::Select:Hover@@", theme.getSecondary());
        tokens.put("@@Input::Checkbox:Background@@", theme.getSecondaryVariant());
        tokens.put("@@Input::Checkbox:Color@@", theme.getSecondary());
        tokens.put("@@Input::TextArea:Counter@@", theme.getSecondaryVariant());

        /*tokens.put("@@DataTable::Heading:Background@@", theme.getSecondary());
        tokens.put("@@DataTable::Heading:Shade2@@", theme.getSecondary());
        tokens.put("@@DataTable::Heading:Shade1@@", theme.getPrimary());
        tokens.put("@@DataTable::Row-Even:Background@@", theme.getPrimary());
        */
        tokens.put("@@DataTable::Row-Odd:Background@@", "#E2E4FF");
        tokens.put("@@DataTable::Row-Selected:Background@@", theme.getPrimaryVariant());
        tokens.put("@@DataTable::Row-Selected:Color@@", WHITE);

        tokens.put("@@ToolTip::Border@@", theme.getPrimary());
        tokens.put("@@ToolTip::Background@@", theme.getSecondary());
        tokens.put("@@ToolTip::Color@@", "FFFFFF");

        // Shades are from http://www.color-hex.com/
        // 4th shade
        tokens.put("@@Notification::Default::Background@@", "#606060");
        tokens.put("@@Notification::Success::Background@@", "#69CDA4");
        tokens.put("@@Notification::Info::Background@@", "#6CA1CA");
        tokens.put("@@Notification::Warning::Background@@", "#DCA66C");
        tokens.put("@@Notification::Error::Background@@", "#D96363");
        //5th shade
        /*tokens.put("@@Notification::Default::Background@@", "#767676");
        tokens.put("@@Notification::Success::Background@@", "#7ED4B1");
        tokens.put("@@Notification::Info::Background@@", "#81AED2");
        tokens.put("@@Notification::Warning::Background@@", "#E1B381");
        tokens.put("@@Notification::Error::Background@@", "#DF7979");*/

        // Actual color on the border
        tokens.put("@@Notification::Default::Border@@", "#1C1C1C");
        tokens.put("@@Notification::Success::Border@@", "#29B87E");
        tokens.put("@@Notification::Info::Border@@", "#2E79B4");
        tokens.put("@@Notification::Warning::Border@@", "#CE812E");
        tokens.put("@@Notification::Error::Border@@", "#CA2121");

        tokens.put("@@Modal::Background@@", theme.getPrimary());
        tokens.put("@@Modal::Header::Background@@", theme.getPrimary());
        tokens.put("@@Modal::Body::Background@@", "#FFFFFF");

        tokens.put("@@Tile::Header::Background@@", theme.getPrimary());

        tokens.put("@@Calendar::Event::Background@@", theme.getGrayedOut());
        return tokens;
    }

    private static List<String> getNewThemeNames() {
        List<String> names = new ArrayList<String>();
        names.add("Back In Black");
        names.add("Dream On");
        names.add("Sweet Emotions");
        names.add("Its My Life");
        names.add("Summer Of 69");
        names.add("November Rain");
        names.add("Sweet Child Of Mine");
        names.add("Over the Hills and Far Away");
        names.add("Stairway To Heaven");
        names.add("Smells Like Teen Spirit");
        names.add("Another Brick In The Wall");
        names.add("Hotel California");
        names.add("House Of The Rising Sun");
        names.add("Smoke On The Water");
        names.add("Purple Haze");
        names.add("The Man Who Sold The World");
        names.add("Nightfall In Middle Earth");
        names.add("Running Blind");
        names.add("Dance Of Death");
        names.add("In The End");
        names.add("Nothing Else Matters");
        names.add("Enter Sandman");
        names.add("Ace of Spades");
        names.add("Cowboys From Hell");
        names.add("If Rain Is What You Want");
        names.add("Sarcastrophe");
        names.add("This Cold Black");
        names.add("Spiders");
        names.add("Chop Suey");
        names.add("Revenga");
        names.add("Home");
        names.add("Chalk Outline");
        names.add("Sign of the Times");
        names.add("Holiday");
        names.add("Sign of the Times");
        names.add("Rebellion");
        names.add("Goodbye");
        names.add("Sulfur");
        names.add("Vendetta");
        names.add("Duality");
        names.add("Hypnotize");
        names.add("ATWA");
        names.add("Marmalade");
        names.add("Woman");
        names.add("Dreaming");
        names.add("Holy Mountains");
        names.add("Question");
        names.add("Bounce");
        names.add("Science");
        names.add("Aerials");
        names.add("Suggestions");
        Collections.sort(names);
        return names;
    }

}
