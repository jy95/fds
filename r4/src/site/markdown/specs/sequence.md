# sequence 

<table>
  <thead>
    <tr>
      <th>Dosage</th>
      <th>Human readable text</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><pre><code class="language-json">[
  {
    &quot;sequence&quot;: 1,
    &quot;text&quot;: &quot;Instructions 1&quot;
  },
  {
    &quot;sequence&quot;: 2,
    &quot;text&quot;: &quot;Instructions 2A&quot;
  },
  {
    &quot;sequence&quot;: 2,
    &quot;text&quot;: &quot;Instructions 2B&quot;
  }
]
</code></pre></td>
      <td>Instructions 1 then Instructions 2A and Instructions 2B</td>
    </tr>
    <tr>
      <td><pre><code class="language-json">[
  {
    &quot;text&quot;: &quot;Instructions 1&quot;
  },
  {
    &quot;text&quot;: &quot;Instructions 2&quot;
  }
]
</code></pre></td>
      <td>Instructions 1 then Instructions 2</td>
    </tr>
    <tr>
      <td><pre><code class="language-json">[
  {
    &quot;sequence&quot;: 1,
    &quot;text&quot;: &quot;Instructions 1&quot;
  },
  {
    &quot;sequence&quot;: 2,
    &quot;text&quot;: &quot;Instructions 2&quot;
  }
]
</code></pre></td>
      <td>Instructions 1 then Instructions 2</td>
    </tr>
  </tbody>
</table>
