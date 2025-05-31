# rateRange 

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
  &quot;doseAndRate&quot;: [
    {
      &quot;rateRange&quot;: {
        &quot;high&quot;: {
          &quot;value&quot;: 5,
          &quot;unit&quot;: &quot;pills&quot;
        }
      }
    }
  ]
}
</code></pre></td>
      <td>at a rate of up to 5 pills</td>
    </tr>
    <tr>
      <td><pre><code class="language-json">{
  &quot;doseAndRate&quot;: [
    {
      &quot;rateRange&quot;: {
        &quot;low&quot;: {
          &quot;value&quot;: 3,
          &quot;unit&quot;: &quot;pills&quot;
        },
        &quot;high&quot;: {
          &quot;value&quot;: 5,
          &quot;unit&quot;: &quot;pills&quot;
        }
      }
    }
  ]
}
</code></pre></td>
      <td>at a rate of 3 to 5 pills</td>
    </tr>
    <tr>
      <td><pre><code class="language-json">{
  &quot;doseAndRate&quot;: [
    {
      &quot;rateRange&quot;: {
        &quot;low&quot;: {
          &quot;value&quot;: 3,
          &quot;unit&quot;: &quot;pills&quot;
        }
      }
    }
  ]
}
</code></pre></td>
      <td>at a rate of at least 3 pills</td>
    </tr>
  </tbody>
</table>
