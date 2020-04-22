package com.memoforward.blog.utils;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Code;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MarkdownUtils {

    public static String markdownToHTML(String markdown){
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }

    public static String markdownToHTMLExtensions(String markdown){
        List<Extension> tableExtensions = Arrays.asList(TablesExtension.create());
        List<Extension> headingExtensions = Collections.singletonList(HeadingAnchorExtension.create());
        Parser parser = Parser.builder().
                extensions(tableExtensions).build();
        HtmlRenderer renderer = HtmlRenderer.builder().
                extensions(tableExtensions).
                extensions(headingExtensions).
                attributeProviderFactory(new AttributeProviderFactory() {
                    @Override
                    public AttributeProvider create(AttributeProviderContext attributeProviderContext) {
                        return new CustomizedAttributeProvider();
                    }
                }).
                build();
        Node document = parser.parse(markdown);
        return renderer.render(document);
    }


    static class CustomizedAttributeProvider implements AttributeProvider{
        @Override
        public void setAttributes(Node node, String s, Map<String, String> map) {
            if(node instanceof TableBlock){
                map.put("class", "ui celled table");
            }
            if(node instanceof Code){
                map.put("class", "language-");
            }
        }
    }
}
