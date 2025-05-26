# durationDurationMax 

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
      &quot;durationMax&quot;: 5,
      &quot;durationUnit&quot;: &quot;d&quot;
    }
  }
}
</code></pre></td>
      <td>maximum 5 days</td>
    </tr>
    <tr>
      <td><pre><code class="language-json">{
  &quot;timing&quot;: {
    &quot;repeat&quot;: {
      &quot;duration&quot;: 1,
      &quot;durationMax&quot;: 3,
      &quot;durationUnit&quot;: &quot;d&quot;
    }
  }
}
</code></pre></td>
      <td>over 1 day ( maximum 3 days )</td>
    </tr>
    <tr>
      <td><pre><code class="language-json">{
  &quot;timing&quot;: {
    &quot;repeat&quot;: {
      &quot;duration&quot;: 3,
      &quot;durationUnit&quot;: &quot;d&quot;
    }
  }
}
</code></pre></td>
      <td>over 3 days</td>
    </tr>
  </tbody>
</table>
