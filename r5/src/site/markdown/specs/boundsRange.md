# boundsRange 

<table>
  <thead>
    <tr>
      <th>Dosage</th>
      <th>Human readable text</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><pre><code class="language-json">{
  &quot;timing&quot;: {
    &quot;repeat&quot;: {
      &quot;boundsRange&quot;: {
        &quot;high&quot;: {
          &quot;system&quot;: &quot;http://hl7.org/fhir/ValueSet/duration-units&quot;,
          &quot;value&quot;: 5,
          &quot;code&quot;: &quot;d&quot;
        }
      }
    }
  }
}
</code></pre></td>
      <td>for up to 5 days</td>
    </tr>
    <tr>
      <td><pre><code class="language-json">{
  &quot;timing&quot;: {
    &quot;repeat&quot;: {
      &quot;boundsRange&quot;: {
        &quot;low&quot;: {
          &quot;system&quot;: &quot;http://hl7.org/fhir/ValueSet/duration-units&quot;,
          &quot;value&quot;: 3,
          &quot;code&quot;: &quot;d&quot;
        },
        &quot;high&quot;: {
          &quot;system&quot;: &quot;http://hl7.org/fhir/ValueSet/duration-units&quot;,
          &quot;value&quot;: 5,
          &quot;code&quot;: &quot;d&quot;
        }
      }
    }
  }
}
</code></pre></td>
      <td>for 3 to 5 days</td>
    </tr>
    <tr>
      <td><pre><code class="language-json">{
  &quot;timing&quot;: {
    &quot;repeat&quot;: {
      &quot;boundsRange&quot;: {
        &quot;low&quot;: {
          &quot;value&quot;: 5,
          &quot;unit&quot;: &quot;days&quot;
        }
      }
    }
  }
}
</code></pre></td>
      <td>for at least 5 days</td>
    </tr>
  </tbody>
</table>
