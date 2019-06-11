import java.util.Queue;
import java.util.Stack;

/*
 * SD2x Homework #2
 * Implement the method below according to the specification in the assignment description.
 * Please be sure not to change the method signature!
 */

public class HtmlValidator {
	
	public static Stack<HtmlTag> isValidHtml(Queue<HtmlTag> tags) {
		if (tags == null || tags.isEmpty()) {
			return null;
		}

		Stack<HtmlTag> htmlStack = new Stack<>();
		while (tags.peek() != null) {
			HtmlTag nextTag = tags.remove();
			if (nextTag.isSelfClosing()) {
				continue;
			}

			// If nextTag is a closing tag.
			if (!nextTag.isOpenTag()) {
				// If we have reached an end tag and have nothing in the stack,
				// we return null to indicate malformed HTML.
				if (htmlStack.isEmpty()) {
					return null;
				}

				HtmlTag endTag = htmlStack.peek();
				// If we have an end tag and the stack is not empty,
				// we can check if the two tags match. If they do not, we return
				// the current stack indicating where we have encountered an error.
				if (!nextTag.matches(endTag)) {
					return htmlStack;
				}

				// Everything is well, no issues arose.
				htmlStack.pop();
			}
			else {
				// The tag is an opening tag, and we push it onto the stack.
				htmlStack.push(nextTag);
			}
		}
		
		return htmlStack;
	}
	

}

